plugins {
    java
}

group = "com.gqggqg"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core:8.0.1")
    implementation("org.postgresql:postgresql:42.2.24.jre7")
    implementation("org.jetbrains:annotations:22.0.0")
}
