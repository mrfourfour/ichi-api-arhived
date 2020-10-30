//package com.mrfourfour.ichi.user.representation
//
//import com.mrfourfour.ichi.keycloak.application.Token
//import com.mrfourfour.ichi.user.application.UserService
//import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
//import org.spockframework.spring.SpringBean
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.web.reactive.server.WebTestClient
//import spock.lang.Specification
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//class UserControllerSpec extends Specification {
//
//    @Autowired
//    private WebTestClient webTestClient
//
//    @SpringBean
//    private UserService userService = Mock()
//
//    def "spec: test login"() {
//        given: "username과 password가 주어진다."
//        def payload = "{\"username\": \"hello\", \"password\": \"1234\"}"
//        def loginParameter = new LoginRequest("hello", "1234").to()
//        def token = new Token(
//                "hello1234",
//                0,
//                0,
//                "refreshToken",
//                "bearer"
//        )
//
//        def expected = new LoginResponse(token)
//
//        userService.login(loginParameter) >> { token }
//
//        when: "웹을 통해 페이로드를 요청한다."
//        def result = webTestClient
//                .post()
//                .uri("/login")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(payload)
//                .exchange()
//
//        then: "정상적으로 응답한다."
//        result.expectStatus()
//                .isOk()
//                .expectBody(LoginResponse.class)
//                .isEqualTo(expected)
//    }
//}
