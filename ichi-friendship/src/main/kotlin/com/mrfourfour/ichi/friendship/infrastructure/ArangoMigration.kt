package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.async.ArangoDBAsync
import com.arangodb.entity.EdgeDefinition
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("default")
class ArangoMigration(
    val arango: ArangoDBAsync
) {
    init {
        runBlocking { migrate() }
    }

    suspend fun migrate() {
        val exists = arango.db(database).exists().await()

        if (exists) {
            logger.info("database already exists. ignore migration.")
            return
        }

        logger.info("database doesn't exist. start to migrate")
        doMigrate()
    }

    private suspend fun doMigrate() {
        arango.createDatabase(database).await()

        val db = arango.db(database)
        db.createGraph("friendship", createEdgeDefinition()).await()
    }

    private fun createEdgeDefinition(): List<EdgeDefinition> {
        return listOf(
            EdgeDefinition()
                .collection("is_friend_with")
                .from("user")
                .to("user")
        )
    }

    companion object : KLogging()
}