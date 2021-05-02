plugins {
    groovy
    kotlin("jvm")

}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")

    implementation("com.arangodb:arangodb-java-driver:6.10.0")
    implementation("com.arangodb:velocypack-module-jdk8:1.1.0")
}
