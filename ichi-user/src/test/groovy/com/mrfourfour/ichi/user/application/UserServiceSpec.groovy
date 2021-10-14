package com.mrfourfour.ichi.user.application

import com.mrfourfour.ichi.keycloak.application.DuplicateUserSignUpException
import com.mrfourfour.ichi.keycloak.application.TokenProvider
import com.mrfourfour.ichi.user.representation.Email
import spock.lang.Specification

class UserServiceSpec extends Specification {

    UserService sut
    TokenProvider tokenProvider
    EmailChecker emailChecker

    def setup() {
        tokenProvider = Mock()
        emailChecker = Mock()
        sut = new UserService(tokenProvider, emailChecker)
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
                "ttt", "1234"
        )

        when:
        sut.signUp(signUpParameter)

        then:
        1 * tokenProvider.signUp(*_)
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

    def "check duplicate email - result : no duplicate"() {
        given:
        Email email = new Email(
                "hello"
        )
//        emailChecker.checkDuplicate(*_) >> {  }

        when:
        sut.checkDuplicate(email.email)

        then:
        1 * emailChecker.checkDuplicate(*_)
    }

    def "check duplicate email - result : duplicate"() {
        given:
        Email email = new Email(
                "hello"
        )
        emailChecker.checkDuplicate(*_) >> {throw new DuplicateUserSignUpException()}

        when:
        sut.checkDuplicate(email.email)

        then:
        thrown(DuplicateUserSignUpException.class)
    }
}
