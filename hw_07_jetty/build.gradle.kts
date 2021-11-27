plugins {
    java
    kotlin("jvm") version "1.4.31" apply false
    `kotlin-dsl`
    application
}

repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    group = "org.gqggqg"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.postgresql:postgresql:42.3.1")

        compileOnly("org.projectlombok:lombok:1.18.22")
        annotationProcessor("org.projectlombok:lombok:1.18.22")
    }
}
