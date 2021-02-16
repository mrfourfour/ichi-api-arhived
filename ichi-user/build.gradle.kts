plugins {
    groovy
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.keycloak:keycloak-admin-client:11.0.3")
}
