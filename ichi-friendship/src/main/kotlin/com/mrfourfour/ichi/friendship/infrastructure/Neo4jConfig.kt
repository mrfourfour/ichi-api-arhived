package com.mrfourfour.ichi.friendship.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Neo4jConfig {

    @Bean
    fun configuration(properties: FriendshipNeo4jProperties): org.neo4j.ogm.config.Configuration =
        properties.createConfiguration()
}