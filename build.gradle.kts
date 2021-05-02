import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    base
    id("org.springframework.boot") version "2.4.3" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false

    kotlin("jvm") version "1.4.31" apply false
    kotlin("plugin.spring") version "1.4.31" apply false
    kotlin("plugin.jpa") version "1.4.31" apply false
}

allprojects {
    group = "com.mrfourfour.ichi"
    version = "1.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
    }

    val implementation by configurations
    val testImplementation by configurations

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.keycloak.bom:keycloak-adapter-bom:11.0.3")
            mavenBom("org.testcontainers:testcontainers-bom:1.15.3")
        }
    }

    dependencies {
        implementation(kotlin("reflect"))
        implementation("org.keycloak:keycloak-spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.4.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")
        implementation("io.github.microutils:kotlin-logging:1.12.0")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("org.amshove.kluent:kluent:1.65")
        testImplementation("org.testcontainers:testcontainers")
        testImplementation("org.testcontainers:junit-jupiter")

    }

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

project(":ichi-server") {
    val api by configurations
    dependencies {
        api(project(":ichi-common"))
        api(project(":ichi-user"))
        api(project(":ichi-friendship"))
    }
}

project(":ichi-user") {
    val api by configurations
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        api(project(":ichi-common"))
    }
}

project(":ichi-friendship") {
    val api by configurations
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        api(project(":ichi-common"))
        api(project(":ichi-user"))
    }
}

project(":ichi-common") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}