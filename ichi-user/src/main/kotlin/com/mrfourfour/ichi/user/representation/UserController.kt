package com.mrfourfour.ichi.user.representation

import com.mrfourfour.ichi.keycloak.application.DuplicateUserSignUpException
import com.mrfourfour.ichi.keycloak.application.Token
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import com.mrfourfour.ichi.user.application.SignUpParameter
import com.mrfourfour.ichi.user.application.LoginParameter
import com.mrfourfour.ichi.user.application.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        private val userService: UserService,
        private val tokenProvider: TokenProvider
) {

    /**
     * 사용자는 이곳을 통해 로그인을 시도한다.
     * 티켓 이용자, 판매자는 이 곳을 통해 동시에 접속을 시도하며,
     * 인증이 완료되면 oauth access token을 반환한다.
     * @param loginRequest 토큰을 발급하기 위해 필요한 요청
     */
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        val token = userService.login(loginRequest.to())
        return LoginResponse(token)
    }

    /**
     * 사용자는 이곳을 통해 토큰을 갱신한다.
     * 갱신이 정상적으로 진행되면 oauth access token을 반환한다.
     */
    @PostMapping("/token/refresh")
    fun refreshToken(@RequestBody refreshTokenPayload: RefreshTokenPayload) =
            refresh(refreshTokenPayload)

    private fun refresh(refreshTokenPayload: RefreshTokenPayload): Token? =
            tokenProvider.refresh(refreshTokenPayload.refreshToken)

    /**
     * 티켓 이용자, 판매자는 이 곳을 통해 회원가입을 시도할 수 있다.
     * 회원가입이 진행되고 나면 서버에서 자동으로 로그인을 시도하며,
     * 인증이 완료되면 oauth access token을 반환한다.
     * @param signUpRequest 회원가입을 할 때 필요한 요청
     */
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<String> {
        return try {
            val token = userService.signUp(signUpRequest.to())
            ResponseEntity.status(HttpStatus.CREATED).build()
        }catch (e: DuplicateUserSignUpException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicated User")
        }
    }
}

data class LoginRequest(
        val email: String,
        val password: String
) {
    fun to() = LoginParameter(email, password)
}

data class RefreshTokenPayload(
        val refreshToken: String
)

data class SignUpRequest(
        val email: String,
        val password: String,
        val gender: String
) {
    fun to() = SignUpParameter(email, password)
}

data class LoginResponse(
        val token: Token? = null
)