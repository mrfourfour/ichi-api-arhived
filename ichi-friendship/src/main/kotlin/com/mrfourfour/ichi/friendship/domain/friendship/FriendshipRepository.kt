package com.mrfourfour.ichi.friendship.domain.friendship

interface FriendshipRepository {
    suspend fun findByUserId(id: String): List<Friendship>
    suspend fun save(entity: Friendship): Friendship
}