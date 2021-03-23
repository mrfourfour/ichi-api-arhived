package com.mrfourfour.ichi.friendship.infrastructure

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
@ConfigurationProperties("friendship.neo4j")
class FriendshipNeo4jProperties(
    private val uri: String,
    private val username: String,
    private val password: String
) {
    fun createDriver(): Driver {
        val authToken = AuthTokens.basic(username, password)
        val uri = URI.create(uri)
        return GraphDatabase.driver(uri, authToken)
    }
}