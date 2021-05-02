package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.async.ArangoDBAsync
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ArangoIntegrationTest {

    protected lateinit var arango: ArangoDBAsync

    @BeforeEach
    fun setup() {
        val factory = ArangoConfig()
        val arango = factory.arango(
            ArangoProperties(
                ARANGO_CONTAINER.host,
                ARANGO_CONTAINER.getMappedPort(8529),
                2000,
                "root",
                "s3cr3t",
                false
            )
        )
        this.arango = arango
    }


    @Test
    @DisplayName("컨테이너 동작 테스트")
    fun assertContainerRunning() {
        ARANGO_CONTAINER.isRunning.shouldBeTrue()
    }

    @Test
    @DisplayName("Arango Health Check")
    fun assertArangoHealthCheck() {
        val version = arango.version.get()
        println(version.version)

        version.shouldNotBeNull()
    }

    companion object {
        @JvmField
        @Container
        protected val ARANGO_CONTAINER = ArangoContainer()
    }
}
