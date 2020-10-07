package com.mrfourfour.mylittleticket.user.token.application

import com.mrfourfour.mylittleticket.user.keycloak.application.Token
import com.mrfourfour.mylittleticket.user.keycloak.application.TokenProvider
import com.mrfourfour.mylittleticket.user.keycloak.application.TokenRequest
import org.springframework.stereotype.Service

@Service
class UserService(
        private val tokenProvider: TokenProvider
) {

    fun login(loginParameter: LoginParameter): Token? {
        val tokenRequest = TokenRequest(loginParameter.username, loginParameter.password)
        return tokenProvider.issue(tokenRequest)
    }

    fun refreshToken(refreshToken: String) = tokenProvider.refresh(refreshToken)

}
