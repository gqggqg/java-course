dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation(project(":init-db"))
    implementation(project(":jooq-generated"))
}