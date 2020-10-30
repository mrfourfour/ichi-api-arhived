package com.mrfourfour.ichi.keycloak.infrastructure

import mu.KLogging
import org.keycloak.KeycloakPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PrincipalService {

    fun getUserPrincipal(): KeycloakPrincipal<*> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is KeycloakPrincipal<*>) {
            principal.keycloakSecurityContext.authorizationContext.permissions
            return principal
        }
        throw IllegalStateException("Not Authenticated from keycloak")
    }

    companion object: KLogging()
}