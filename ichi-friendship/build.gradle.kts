plugins {
    groovy
    kotlin("jvm")

}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.arangodb:arangodb-java-driver:6.10.0")
    implementation("com.arangodb:velocypack-module-jdk8:1.1.0")
}
