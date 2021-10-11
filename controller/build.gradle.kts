plugins {
    java
    application
    id("io.freefair.lombok") version "6.2.0"
}

application {
    mainClass.set("App")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

group = "com.gqggqg"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation(project(":models"))

    compileOnly("org.jetbrains:annotations:16.0.2")
}