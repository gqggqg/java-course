dependencies {
    implementation("org.eclipse.jetty:jetty-server:11.0.6")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.6")

    implementation("org.jooq:jooq:3.15.4")

    implementation(project(":init-db"))
    implementation(project(":jooq-generated"))
}