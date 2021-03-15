package com.mrfourfour.ichi.user.application

import com.mrfourfour.ichi.keycloak.application.DuplicateUserSignUpException
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import spock.lang.Specification

class UserServiceSpec extends Specification {

    UserService sut
    TokenProvider tokenProvider

    def setup() {
        tokenProvider = Mock()
        sut = new UserService(tokenProvider)
    }

    def "issue token when user login" () {
        given:
        LoginParameter loginParameter = new LoginParameter(
                "hello", "world"
        )

        when:
        sut.login(loginParameter)

        then:
        1 * tokenProvider.issue(*_)
    }

    def "issue token when user is trying sign up"() {
        given:
        SignUpParameter signUpParameter = new SignUpParameter(
                "hello", "world"
        )

        when:
        sut.signUp(signUpParameter)

        then:
        1 * tokenProvider.signUp(*_)
        1 * tokenProvider.issue(*_)
    }

    def "failed to create user when user is trying to sign up"() {
        given:
        SignUpParameter signUpParameter = new SignUpParameter(
                "hello", "world"
        )
        tokenProvider.signUp(*_) >> { throw new DuplicateUserSignUpException() }

        when:
        sut.signUp(signUpParameter)

        then:
        thrown(DuplicateUserSignUpException.class)
    }
}
