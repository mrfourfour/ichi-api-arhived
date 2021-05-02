package com.mrfourfour.ichi.user.application

import com.mrfourfour.ichi.common.domain.EventPublisher
import com.mrfourfour.ichi.keycloak.application.Token
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import com.mrfourfour.ichi.keycloak.application.TokenRequest
import com.mrfourfour.ichi.user.domain.UserCreated
import com.mrfourfour.ichi.user.domain.UserId
import org.springframework.stereotype.Service

@Service
class UserService(
        private val tokenProvider: TokenProvider,
        private val eventPublisher: EventPublisher
) {

    fun login(loginParameter: LoginParameter): Token? {
        val tokenRequest = TokenRequest(loginParameter.email, loginParameter.password)
        return tokenProvider.issue(tokenRequest)
    }

    fun signUp(signUpParameter: SignUpParameter): Token? {
        val tokenRequest = TokenRequest(signUpParameter.email, signUpParameter.password)
        val userId = tokenProvider.signUp(tokenRequest)
        publishCreatedEvent(userId)
        return tokenProvider.issue(tokenRequest)
    }

    private fun publishCreatedEvent(userId: UserId) {
        val userCreated = UserCreated(userId)
        eventPublisher.publish(userCreated)
    }
}
