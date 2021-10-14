package com.mrfourfour.ichi.matchmakerproxy.representation

import com.mrfourfour.ichi.matchmakerproxy.application.MatchMakerProxyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
class MatchMakerProxyController(
        private val matchMakerProxyService : MatchMakerProxyService
) {

    @PostMapping("/match/request")
    fun matchRequest() : ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}

data class MatchRequest(
        val userId : String
)