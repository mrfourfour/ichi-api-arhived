package com.mrfourfour.ichi.user.domain

class User private constructor(
    username: String
) {
    var id = UserId()
        private set

    var username = username
        private set

    companion object {
        fun of(username: String) =
            User(username)
    }
}

class UserId(
    val value: String = ""
)