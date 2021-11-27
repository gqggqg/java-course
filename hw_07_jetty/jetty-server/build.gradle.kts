dependencies {
    implementation("org.eclipse.jetty:jetty-server:9.4.33.v20201020")
    implementation("org.eclipse.jetty:jetty-servlet:9.4.33.v20201020")

    implementation("org.jooq:jooq:3.15.4")

    implementation(project(":init-db"))
    implementation(project(":jooq-generated"))
}