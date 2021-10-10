plugins {
    java
    id("io.freefair.lombok") version "6.2.0"
}

group = "com.gqggqg"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.intellij:annotations:12.0")
}
