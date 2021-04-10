package com.mrfourfour.ichi.friendship.domain

import spock.lang.Specification

class PersonTest extends Specification{

    def 'test try to make friendship'() {
        given: "two person was given"
        User foo = new User("foo")
        User bar = new User("bar")

        when: "foo try to make friendship"
        foo.requestFriend(bar)
        then: "bar has one waited friendship"
        true
    }

    def 'test accept friendship'() {
        given: "two person was given"
        User foo = new User("foo")
        User bar = new User("bar")

        when: "foo try to make friendship"
        person.addFollower(person)

        then: "bar has one waited friendship"
        true

        when: "bar accepted friendship"

        then: "foo and bar are friends with each other"
        true
    }

    def 'test reject friendship'() {
        given: "two person was given"
        User foo = new User("foo")
        User bar = new User("bar")

        when: "foo try to make friendship"

        then: "bar has one waited friendship"

        when: "bar rejected friendship"

        then: "foo and bar are not friends"
    }
}
