package com.mrfourfour.ichi.friendship.infrastructure

import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Neo4jConfig {

    @Bean
    fun configuration(properties: FriendshipNeo4jProperties): Driver =
        properties.createDriver()
}