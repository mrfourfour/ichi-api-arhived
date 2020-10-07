package com.mrfourfour.mylittleticket.user.keycloak.application

data class TokenRequest(
        val username: String,
        val password: String
)
