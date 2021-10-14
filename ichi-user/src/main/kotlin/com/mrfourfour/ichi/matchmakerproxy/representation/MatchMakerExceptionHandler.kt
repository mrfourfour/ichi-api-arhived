package com.mrfourfour.ichi.matchmakerproxy.representation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [MatchMakerProxyController::class])
class MatchMakerExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun requestHandler(e: IllegalArgumentException) : ResponseEntity<Void>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }
}