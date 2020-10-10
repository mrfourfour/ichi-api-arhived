package com.mrfourfour.mylittleticket.user.token.representation

import com.mrfourfour.mylittleticket.user.keycloak.application.Token
import com.mrfourfour.mylittleticket.user.token.application.UserService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerTests {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `client try to login`() {
        val payload = "{\"username\": \"hello\", \"password\": \"1234\"}"
        val loginParameter = LoginRequest("hello", "1234").to()
        val expectedToken = Token(
                "accessToken",
                0,
                0,
                "refreshToken",
                "bearer"
        )
        given(userService.login(loginParameter)).willReturn(expectedToken)

        val result = webTestClient
                .post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()

        then(result).should()
                .expectStatus()
                .isOk
                .expectBody<LoginResponse>()
                .isEqualTo(LoginResponse(expectedToken))
    }
}