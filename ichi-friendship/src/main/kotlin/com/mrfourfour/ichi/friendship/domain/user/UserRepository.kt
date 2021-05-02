package com.mrfourfour.ichi.friendship.domain.user

interface UserRepository {
    suspend fun save(user: User): User

}