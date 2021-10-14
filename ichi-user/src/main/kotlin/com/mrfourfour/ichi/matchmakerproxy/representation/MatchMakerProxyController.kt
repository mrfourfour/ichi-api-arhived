package com.mrfourfour.ichi.matchmakerproxy.representation

import com.mrfourfour.ichi.matchmakerproxy.application.MatchMakerProxyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
class MatchMakerProxyController(
        private val matchMakerProxyService : MatchMakerProxyService
) {

    @PostMapping("/match/request")
    fun matchRequest() : ResponseEntity<Void> {
        val userId = SecurityContextHolder.getContext().getAuthentication().principal
        matchMakerProxyService.request(userId as String)
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun requestHandler(e: IllegalArgumentException) : ResponseEntity<Void>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }
}

data class MatchRequest(
        val userId : String
)