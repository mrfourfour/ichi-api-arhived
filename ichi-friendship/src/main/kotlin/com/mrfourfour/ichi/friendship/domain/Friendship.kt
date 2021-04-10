package com.mrfourfour.ichi.friendship.domain

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
class Friendship(

    @TargetNode
    val user: User
) {
    @Id
    @GeneratedValue
    private val id: Long = 0

    /**
     * When friendship create first, start from waited state.
     */
    private val state: State = State.WAITED

    val isFriend: Boolean
        get() = true

    private enum class State{
        FRIEND,
        WAITED
    }
}

