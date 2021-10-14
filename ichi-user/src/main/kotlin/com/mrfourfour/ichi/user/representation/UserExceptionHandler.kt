package com.mrfourfour.ichi.user.representation

import com.mrfourfour.ichi.keycloak.application.DuplicateUserSignUpException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestControllerAdvice
class UserExceptionHandler {

        @ExceptionHandler(value = [DuplicateUserSignUpException::class])
        @ResponseStatus(HttpStatus.OK)
        fun duplicateUserHandler(e: DuplicateUserSignUpException) : DuplicateCheckResult {
            return DuplicateCheckResult(true);
        }
}

data class ErrorResponse(
        var errorMessage: String,
        var errorCode: String
)