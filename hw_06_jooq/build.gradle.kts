plugins {
    java
    kotlin("jvm") version "1.4.31" apply false
    id("nu.studer.jooq") version "6.0.1" apply false
    `kotlin-dsl`
    application
}

repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }

    group = "org.gqggqg"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.jooq:jooq:3.15.4")

        implementation("org.flywaydb:flyway-core:8.0.2")
        implementation("org.postgresql:postgresql:42.3.1")
        implementation("org.jooq:jooq:3.15.4")
        implementation("org.jooq:jooq-codegen:3.15.4")
        implementation("org.jooq:jooq-meta:3.15.4")
    }
}
