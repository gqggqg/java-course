plugins {
    application
}

group = "com.gqggqg"
version = "1.0.0"

allprojects {
    apply {
        plugin("application")
    }

    repositories {
        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {
        implementation("org.jetbrains:annotations:22.0.0")

        compileOnly("io.vertx:vertx-core:3.9.4")
        implementation("io.vertx:vertx-hazelcast:3.9.4")
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }
}
