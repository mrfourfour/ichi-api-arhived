package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.async.ArangoDBAsync
import com.arangodb.async.ArangoEdgeCollectionAsync
import com.arangodb.async.ArangoVertexCollectionAsync
import com.arangodb.entity.EdgeEntity
import com.mrfourfour.ichi.friendship.domain.friendship.Friendship
import com.mrfourfour.ichi.friendship.domain.friendship.FriendshipRepository
import com.mrfourfour.ichi.friendship.domain.user.User
import com.mrfourfour.ichi.friendship.infrastructure.config.database
import kotlinx.coroutines.future.await
import mu.KLogging
import org.springframework.stereotype.Repository

@Repository
class ArangoFriendshipRepository(
    private val arango: ArangoDBAsync
) : FriendshipRepository {

    private val edges: ArangoEdgeCollectionAsync
    private val vertexes: ArangoVertexCollectionAsync

    init {
        val db = arango.db(database)
        val graph = db.graph("friendship")
        edges = graph.edgeCollection("is_friend_with")
        vertexes = graph.vertexCollection("user")
    }

    override suspend fun findByUserId(id: String): List<Friendship> {
        val result = vertexes.getVertex<User>(id).await()
        println(result)
        return listOf()
    }

    override suspend fun save(friendship: Friendship): Friendship {

        val inserted = edges
            .insertEdge(friendship.dataEntity)
            .await()
        println(inserted)
        return friendship
    }

    private val Friendship.dataEntity get() = FriendshipEntity(
        "user/${this.from.id}",
        "user/${this.to.id}"
    )

    private val EdgeEntity.friendship get() = Friendship(
        User("hi"), User("hello")
    )

    companion object: KLogging()
}
private inline fun <reified T> ArangoVertexCollectionAsync.getVertex(id: String) = getVertex(id, T::class.java)
