package com.mrfourfour.ichi.config

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserTokenService {
    val currentUserId: String
        get() = keycloakAuthenticationToken
            .account
            .keycloakSecurityContext
            .token
            .subject

    private val keycloakAuthenticationToken get() = SecurityContextHolder
        .getContext()
        .authentication
        .let { it as? KeycloakAuthenticationToken } ?: throw AccessDeniedException("authentication")
}