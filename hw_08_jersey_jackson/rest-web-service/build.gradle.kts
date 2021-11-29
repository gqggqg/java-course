dependencies {
    implementation("org.eclipse.jetty:jetty-server:9.4.33.v20201020")
    implementation("org.eclipse.jetty:jetty-servlet:9.4.33.v20201020")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")

    implementation("com.google.inject:guice:4.2.2")
    implementation("com.google.inject.extensions:guice-servlet:4.2.2")

    implementation("org.glassfish.hk2:guice-bridge:2.6.0")
    implementation("org.glassfish.jersey.containers:jersey-container-servlet:2.32")
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.32")
    implementation("org.glassfish.jersey.media:jersey-media-jaxb:2.32")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.32")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:2.32")

    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")

    implementation(project(":init-db"))
}
