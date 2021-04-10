package com.mrfourfour.ichi.friendship.domain

class Friendship(
    val user: User
) {
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

