package com.mrfourfour.ichi.matchmakerproxy.application


import org.springframework.http.HttpStatus

interface MatchMakerProxy {
    abstract fun request(userId : String) : HttpStatus?
}