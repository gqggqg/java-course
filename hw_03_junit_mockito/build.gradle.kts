plugins {
    java
    application
}

group = "com.gqggqg"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.inject:guice:5.0.1")
    implementation("com.intellij:annotations:12.0")
    implementation(project(":library"))
    implementation(project(":models"))
}

application {
    mainClass.set("Application")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
