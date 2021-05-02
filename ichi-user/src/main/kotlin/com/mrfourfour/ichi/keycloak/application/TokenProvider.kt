package com.mrfourfour.ichi.keycloak.application

import com.mrfourfour.ichi.user.domain.UserId

interface TokenProvider {
    fun issue(tokenRequest: TokenRequest): Token?
    fun refresh(refreshToken: String): Token?
    fun signUp(tokenRequest: TokenRequest): UserId
}
