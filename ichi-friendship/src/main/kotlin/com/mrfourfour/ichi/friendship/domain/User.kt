package com.mrfourfour.ichi.friendship.domain

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*

@Node("Person")
class User(
    @Id
    val id: String
) {

    @Relationship(type = "friends_of", direction = Relationship.Direction.INCOMING)
    private val friends: MutableList<Friendship> = mutableListOf()

    @Relationship(type = "friends_of", direction = Relationship.Direction.OUTGOING)
    private val friendee: MutableList<Friendship> = mutableListOf()

    fun requestFriends(peer: User) {
        val friendship = Friendship(peer)
        friends.add(friendship)
    }

    fun acceptFriendsaddFollower(user: User) {
        this.friends.add(user)
        user.friends.add(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is User) return false

        return this.id == other.id
    }

    override fun hashCode(): Int = Objects.hashCode(id)
}