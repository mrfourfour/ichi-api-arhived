package com.mrfourfour.ichi.keycloak.representation

import com.mrfourfour.ichi.keycloak.infrastructure.PrincipalService
import org.keycloak.KeycloakPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(
        private val principalService: PrincipalService
) {

    @GetMapping("/user/me")
    fun getMe(): KeycloakPrincipal<*> {
        val userPrincipal = principalService.getUserPrincipal()
        return userPrincipal
    }



}

data class TokenDetail(
        val role: List<String>
)