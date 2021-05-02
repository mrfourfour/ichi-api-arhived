plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("kotlin-reflect"))
    implementation("org.keycloak:keycloak-admin-client:11.0.3")
}
