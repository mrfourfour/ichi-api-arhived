package com.mrfourfour.ichi.user.domain

interface UserRepository {
    fun save(user: User): User
}