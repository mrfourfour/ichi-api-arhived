package com.mrfourfour.ichi.friendship.infrastructure

import org.testcontainers.containers.GenericContainer

class ArangoContainer: GenericContainer<ArangoContainer>(CONTAINER_NAME) {

    init {
        withExposedPorts(PORT)
        withEnv("ARANGO_ROOT_PASSWORD", PASSWORD)
    }
    companion object {
        private const val CONTAINER_NAME = "arangodb/arangodb:3.7.10"
        private const val PORT = 8529
        private const val USERNAME = "root"
        private const val PASSWORD = "s3cr3t"
    }
}