package com.mrfourfour.ichi.user.application

import com.mrfourfour.ichi.keycloak.application.Token
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import com.mrfourfour.ichi.keycloak.application.TokenRequest
import org.springframework.stereotype.Service

@Service
class UserService(
        private val tokenProvider: TokenProvider
) {

    fun login(loginParameter: LoginParameter): Token? {
        val tokenRequest = TokenRequest(loginParameter.username, loginParameter.password)
        return tokenProvider.issue(tokenRequest)
    }

    fun signUp(signUpParameter: SignUpParameter): Token? {
        val tokenRequest = TokenRequest(signUpParameter.email, signUpParameter.password)
        tokenProvider.signUp(tokenRequest)
        return tokenProvider.issue(tokenRequest)
    }
}
