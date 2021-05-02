package com.mrfourfour.ichi.friendship.domain.friendship

import com.mrfourfour.ichi.friendship.domain.user.User

class Friendship(
    val from: User,
    val to: User
) {
    var id: Long = 0
        internal set

    /**
     * When friendship create first, start from waited state.
     */
    private val state: State = State.WAITED

    val isFriend: Boolean
        get() = true

    private enum class State {
        FRIEND,
        WAITED
    }
}

