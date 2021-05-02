package com.mrfourfour.ichi.friendship.domain

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@DisplayName("친구 관계 테스트")
class PersonTest {

    @Nested
    @DisplayName("두 사람 A, B가 주어진다.")
    inner class TryToMakeFriendship {
        private lateinit var foo: User
        private lateinit var bar: User

        @BeforeEach
        fun setup() {
           foo = User("foo")
           bar = User("bar")
        }

        @Test
        @DisplayName("A가 친구 신청을 하면 친구 신청 대기 상태가 된다.")
        fun makeFriendship() {
            foo.requestFriends(bar)
            assertTrue(true)
        }

        @Test
        @DisplayName("A가 친구 신청을 하고 B가 수락하면, 둘은 친구가 된다.")
        fun acceptFriendship() {
            foo.requestFriends(bar)
            bar.acceptFriendsaddFollower(foo)
            assertTrue(false)
        }

        @Test
        @DisplayName("A가 친구 신청을 하고 B가 거절하면, 둘은 친구가 되지 않는다.")
        fun rejectFriendship() {

            assertTrue(false)
        }

    }
}
