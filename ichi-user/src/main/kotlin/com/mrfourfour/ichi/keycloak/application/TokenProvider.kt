package com.mrfourfour.ichi.keycloak.application

interface TokenProvider {
    fun issue(tokenRequest: TokenRequest): Token?
    fun refresh(refreshToken: String): Token?
    fun signUp(tokenRequest: TokenRequest)
}
