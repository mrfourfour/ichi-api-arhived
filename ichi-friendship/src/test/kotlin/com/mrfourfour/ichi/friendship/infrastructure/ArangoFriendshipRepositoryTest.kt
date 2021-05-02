package com.mrfourfour.ichi.friendship.infrastructure

import com.mrfourfour.ichi.friendship.domain.friendship.Friendship
import com.mrfourfour.ichi.friendship.domain.friendship.FriendshipRepository
import com.mrfourfour.ichi.friendship.domain.user.User
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("friendship 도메인 ArangoDB 영속 통합 테스트.")
class ArangoFriendshipRepositoryTest : ArangoIntegrationTest() {

    private lateinit var sut: FriendshipRepository

    @BeforeEach
    override fun setup() {
        super.setup()
        sut = ArangoFriendshipRepository(arango)
    }

    @Nested
    @DisplayName("save() 테스트 - Friendship이 주어질 때")
    inner class SaveTest {
        private lateinit var entity: Friendship

        @BeforeEach
        fun setup() {
            entity = Friendship(User("foo"), User("bar"))
        }

        @DisplayName("save를 시도하면 해당 엔티티가 영속된다.")
        @Test
        fun validTest() {
            val result = runBlocking { sut.save(entity) }
        }
    }
}