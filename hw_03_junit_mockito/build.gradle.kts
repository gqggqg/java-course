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
    implementation(project(":library"))
    implementation(project(":models"))

    implementation("com.google.inject:guice:5.0.1")
    implementation("com.intellij:annotations:12.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("org.mockito:mockito-core:4.0.0")

    testImplementation("net.lamberto.junit:guice-junit-runner:1.0.2")
    testImplementation("net.lamberto.junit:guice-commons-configuration:1.0.0")
}

application {
    mainClass.set("Application")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
