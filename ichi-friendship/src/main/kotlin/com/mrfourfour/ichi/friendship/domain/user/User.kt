package com.mrfourfour.ichi.friendship.domain.user

import com.mrfourfour.ichi.friendship.domain.friendship.Friendship
import java.util.*

class User(
    val id: String
) {

    private val friends: MutableList<Friendship> = mutableListOf()

    private val friendee: MutableList<Friendship> = mutableListOf()

    fun requestFriends(peer: User) {
        val friendship = Friendship(this, peer)
        friends.add(friendship)
    }

    fun acceptFriendsaddFollower(user: User) {
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is User) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = Objects.hashCode(id)
}