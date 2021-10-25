plugins {
    java
}

group = "com.gqggqg"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":models"))

    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.google.inject:guice:5.0.1")
    implementation("com.intellij:annotations:12.0")
}
