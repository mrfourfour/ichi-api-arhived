package com.mrfourfour.ichi.user.representation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*


@ControllerAdvice
class UserExceptionHandler {

        @ExceptionHandler
        @ResponseStatus
        fun duplicateUserHandler(e : DuplicateFormatFlagsException) : ResponseEntity<ErrorCode> {
            return ResponseEntity.status(HttpStatus.CREATED).body(ErrorCode("Duplicate User", "201"))
        }

}

data class ErrorCode(
        var errorMessage: String,
        var errorCode: String
)