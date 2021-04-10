package com.mrfourfour.ichi.friendship.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConstructorBinding
@ConfigurationProperties("friendship.database")
class ArangoProperties(
    val host: String,
    @DefaultValue("8529")
    val port: Int,
    @DefaultValue("0")
    val timeout: Int,
    @DefaultValue("")
    val user: String,
    @DefaultValue("")
    val password: String,
    @DefaultValue("false")
    val useSsl: Boolean
)