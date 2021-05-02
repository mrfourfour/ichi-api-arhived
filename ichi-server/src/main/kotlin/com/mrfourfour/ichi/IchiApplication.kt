package com.mrfourfour.ichi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class IchiApplication

fun main(args: Array<String>) {
    runApplication<IchiApplication>(*args)
}
