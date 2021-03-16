package com.mrfourfour.ichi.friendship.infrastructure

import org.neo4j.ogm.config.Configuration.Builder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("friendship.neo4j")
class FriendshipNeo4jProperties(
    private val uri: String,
    private val username: String,
    private val password: String
) {
    fun createConfiguration() = Builder()
        .uri(this.uri)
        .credentials(username, password)
        .build()
}