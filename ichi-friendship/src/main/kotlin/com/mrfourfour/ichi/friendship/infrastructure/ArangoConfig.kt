package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.async.ArangoDBAsync
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