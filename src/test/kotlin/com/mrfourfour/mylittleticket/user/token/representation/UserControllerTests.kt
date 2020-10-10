package com.mrfourfour.mylittleticket.user.token.representation

import com.mrfourfour.mylittleticket.user.keycloak.application.Token
import com.mrfourfour.mylittleticket.user.token.application.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `client try to login`() {
        // given
        val loginParameter = LoginRequest("hello", "1234").to()
        val expectedToken = Token(
                "accessToken",
                0,
                0,
                "refreshToken",
                "bearer"
        )
        `when`(userService.login(loginParameter)).thenReturn(expectedToken)

        // when
        val responseSpec = webTestClient
                .post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginParameter))
                .exchange()

        // then
        responseSpec.expectStatus().isOk
                .expectBody<LoginResponse>()
                .isEqualTo(LoginResponse(expectedToken))
    }

    @Test
    fun `client try to login - string`() {
        // given
        val loginParameter = "{\"username\": \"hello\", \"password\": \"1234\"}"
        val expectedToken = Token(
                "accessToken",
                0,
                0,
                "refreshToken",
                "bearer"
        )
        `when`(userService.login(any())).thenReturn(expectedToken)

        // when
        val responseSpec = webTestClient
                .post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginParameter))
                .exchange()

        // then
        responseSpec.expectStatus().isOk
                .expectBody<LoginResponse>()
                .isEqualTo(LoginResponse(expectedToken))
    }
}