package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.entity.DocumentField
import com.arangodb.entity.EdgeEntity

class FriendshipEntity(
    @DocumentField(DocumentField.Type.FROM)
    val from: String,
    @DocumentField(DocumentField.Type.TO)
    val to: String,
): EdgeEntity() {
    val type = TYPE

    companion object {
        private const val TYPE = "is_friend_with"
    }
}