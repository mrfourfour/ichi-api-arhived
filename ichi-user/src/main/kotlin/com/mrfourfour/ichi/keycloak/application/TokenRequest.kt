package com.mrfourfour.ichi.keycloak.application

data class TokenRequest(
        val email: String,
        val password: String
)
