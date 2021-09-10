package com.mrfourfour.ichi.user.infrastructure

import com.mrfourfour.ichi.keycloak.application.DuplicateUserSignUpException
import com.mrfourfour.ichi.user.application.EmailChecker
import mu.KLogging
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import javax.ws.rs.core.Response

@Component
class UserEmailChecker(
        private val keycloakAdminClient: Keycloak,
        private val properties: KeycloakSpringBootProperties
) : EmailChecker{


    override fun checkDuplicate(email: String) {
        val userResources = keycloakAdminClient
                .realm(properties.realm)
                .users()
        val resultInfo = userResources
                .search(email)
        if(resultInfo.size>0){
            throw DuplicateUserSignUpException()
        }
    }

}