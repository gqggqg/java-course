plugins {
    java
}

group = "com.gqggqg"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.inject:guice:5.0.1")
    implementation("org.jetbrains:annotations:22.0.0")
    compileOnly("org.projectlombok:lombok:1.18.22")
}
