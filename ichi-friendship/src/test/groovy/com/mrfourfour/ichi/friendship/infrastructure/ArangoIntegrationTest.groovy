package com.mrfourfour.ichi.friendship.infrastructure

import com.arangodb.async.ArangoDBAsync
import com.arangodb.velocystream.Request
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
abstract class ArangoIntegrationTest extends Specification {

    @Shared
    private ArangoContainer container = new ArangoContainer()

    protected ArangoDBAsync arango

    def setup() {
        ArangoConfig factory = new ArangoConfig()
        ArangoDBAsync arango = factory.arango(new ArangoProperties(
                container.host,
                container.getMappedPort(8529),
                2000,
                "root",
                "s3cr3t",
                false
        ))
        this.arango = arango
    }

    def "assert container running"() {
        expect:
        container.isRunning()
    }

    def 'arango health check - version'() {
        when:
        def version = arango.version.get()

        println(version.version)
        then:
        version != null
    }
}
