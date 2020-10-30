plugins {
    groovy
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}