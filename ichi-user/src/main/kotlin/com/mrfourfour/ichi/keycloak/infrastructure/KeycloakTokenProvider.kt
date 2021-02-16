package com.mrfourfour.ichi.keycloak.infrastructure

import com.mrfourfour.ichi.keycloak.application.CreateUserFailedException
import com.mrfourfour.ichi.keycloak.application.Token
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import com.mrfourfour.ichi.keycloak.application.TokenRequest
import mu.KLogging
import org.apache.http.HttpHeaders
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import javax.ws.rs.core.Response

@Component
class KeycloakTokenProvider(
        @Qualifier("keycloakWebClient")
        private val webClient: WebClient,
        private val keycloakSpringBootProperties: KeycloakSpringBootProperties,
        private val keycloakAdminClient: Keycloak
) : TokenProvider {

    override fun issue(tokenRequest: TokenRequest): Token? {
        val (username, password) = tokenRequest
        val resource = keycloakSpringBootProperties.resource
        val clientSecret = keycloakSpringBootProperties.credentials["secret"] as String
        val formData = createFormData(resource, clientSecret, username, password)
        return fetchResource(formData)
    }

    override fun refresh(refreshToken: String): Token? {
        val resource = keycloakSpringBootProperties.resource
        val clientSecret = keycloakSpringBootProperties.credentials["secret"] as String
        val createFormData = createFormData(resource, clientSecret, refreshToken)
        return fetchResource(createFormData)
    }

    private fun fetchResource(formData: BodyInserters.FormInserter<String>): Token? =
            webClient.post()
                    .uri { uriBuilder -> uriBuilder.pathSegment("token").build() }
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body(formData)
                    .retrieve()
                    .onStatus({ status -> status.is4xxClientError }) {
                        // TODO 적절한 예외로 만들기
                        it
                                .bodyToMono(String::class.java)
                                .map { IllegalArgumentException("Not authorized: $it") }
                    }
                    .onStatus({ status -> status.is5xxServerError }) {
                        // TODO 적절한 예외로 만들기
                        it
                                .bodyToMono(String::class.java)
                                .map { IllegalArgumentException("Keycloak Server error: $it") }
                    }
                    .bodyToMono<Token>(Token::class.java)
                    .block()

    private fun createFormData(resource: String, clientSecret: String, username: String, password: String): BodyInserters.FormInserter<String> {
        return BodyInserters.fromFormData("client_id", resource)
                .with("grant_type", "password")
                .with("client_secret", clientSecret)
                .with("username", username)
                .with("password", password)
    }
    private fun createFormData(resource: String, clientSecret: String, refreshToken: String): BodyInserters.FormInserter<String> {
        return BodyInserters.fromFormData("refresh_token", refreshToken)
                .with("client_id", resource)
                .with("client_secret", clientSecret)
                .with("grant_type", "refresh_token")
    }

    override fun signUp(tokenRequest: TokenRequest) {
        val userResources = keycloakAdminClient
                .realm(keycloakSpringBootProperties.realm)
                .users()
        val userRepresentation = getUserRepresentation(tokenRequest)
        val createUserResponse = userResources
                .create(userRepresentation)
        if (createUserResponse.statusInfo != Response.Status.CREATED) {
            throw CreateUserFailedException()
        }
    }

    private fun getUserRepresentation(tokenRequest: TokenRequest) =
            UserRepresentation().apply {
                email = tokenRequest.email
                username = tokenRequest.email
                credentials = listOf(
                        CredentialRepresentation().apply {
                            isEnabled = true
                            isEmailVerified = true
                            type = CredentialRepresentation.PASSWORD
                            value = tokenRequest.password
                            isTemporary = false
                        }
                )
                clientRoles = mapOf(
                        Pair(keycloakSpringBootProperties.resource, listOf(ROLE_ICHI_USER))
                )
            }

    companion object: KLogging() {
        private const val ROLE_ICHI_USER = "role_ichi_user"
    }
}

@Configuration
class KeyCloakWebClientConfig {

    @Bean
    fun keycloakAdminClient(
            keycloakSpringBootProperties: KeycloakSpringBootProperties
    ): Keycloak = KeycloakBuilder.builder()
            .serverUrl(keycloakSpringBootProperties.authServerUrl)
            .realm(keycloakSpringBootProperties.realm)
            .clientId(keycloakSpringBootProperties.resource)
            .clientSecret(keycloakSpringBootProperties.credentials["secret"] as String)
            .grantType("client_credentials")
            .build()

    @Bean
    fun keycloakWebClient(
            keycloakSpringBootProperties: KeycloakSpringBootProperties
    ): WebClient {
        val baseUrl = getBaseUrl(keycloakSpringBootProperties)
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .build()
    }

    private fun getBaseUrl(keycloakSpringBootProperties: KeycloakSpringBootProperties) =
            "${keycloakSpringBootProperties.authServerUrl}/realms/" +
                    "${keycloakSpringBootProperties.realm}/protocol/openid-connect"

    companion object: KLogging()
}