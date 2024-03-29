package com.mrfourfour.ichi.user.application

import com.mrfourfour.ichi.keycloak.application.Token
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import com.mrfourfour.ichi.keycloak.application.TokenRequest
import org.springframework.stereotype.Service

@Service
class UserService(
        private val tokenProvider: TokenProvider,
        private val emailChecker: EmailChecker
) {

    fun login(loginParameter: LoginParameter): Token? {
        val tokenRequest = TokenRequest(loginParameter.email, loginParameter.password)
        return tokenProvider.issue(tokenRequest)
    }

    fun signUp(signUpParameter: SignUpParameter) {
        val tokenRequest = TokenRequest(signUpParameter.email, signUpParameter.password)
        tokenProvider.signUp(tokenRequest)
    }

    fun checkDuplicate(email: String) : Boolean{
            emailChecker.checkDuplicate(email)
            return false;
    }
}
