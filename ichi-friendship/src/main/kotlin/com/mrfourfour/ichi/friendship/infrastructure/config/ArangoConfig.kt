package com.mrfourfour.ichi.friendship.infrastructure.config

import com.arangodb.async.ArangoDBAsync
import com.arangodb.async.ArangoGraphAsync
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArangoConfig {

    @Bean
    fun arango(properties: ArangoProperties): ArangoDBAsync =
        ArangoDBAsync.Builder()
            .host(properties.host, properties.port)
            .timeout(properties.timeout)
            .user(properties.user)
            .password(properties.password)
            .useSsl(properties.useSsl)
            .build()

}