package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.entity.VertexEntity

class UserEntity : VertexEntity() {
    companion object {
        const val TYPE = "user"
    }
}