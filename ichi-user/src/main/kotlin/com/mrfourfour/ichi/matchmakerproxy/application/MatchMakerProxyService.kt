package com.mrfourfour.ichi.matchmakerproxy.application

import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MatchMakerProxyService(
        private val matchMakerProxy: MatchMakerProxy
) {

    fun request() {
        val userId = SecurityContextHolder.getContext().getAuthentication().principal
        matchMakerProxy.request(userId as String)

    }
}