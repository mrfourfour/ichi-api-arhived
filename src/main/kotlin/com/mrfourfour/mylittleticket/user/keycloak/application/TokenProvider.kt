package com.mrfourfour.mylittleticket.user.keycloak.application

interface TokenProvider {
    fun issue(tokenRequest: TokenRequest): Token?
    fun refresh(refreshToken: String)
}
