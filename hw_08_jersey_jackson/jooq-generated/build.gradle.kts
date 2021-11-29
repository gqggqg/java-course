plugins {
    id("nu.studer.jooq") version "6.0.1"
}

dependencies {
    jooqGenerator("org.postgresql:postgresql:42.3.1")

    implementation("org.jooq:jooq:3.15.4")
    implementation("org.jooq:jooq-codegen:3.15.4")
    implementation("org.jooq:jooq-meta:3.15.4")
}

jooq {
    version.set("3.15.4")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN

                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://127.0.0.1:5432/CoursesDB"
                    user = "postgres"
                    password = "postgres"
                }

                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"

                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        schemata.addAll(arrayOf(
                            org.jooq.meta.jaxb.SchemaMappingType().apply {
                                inputSchema = "db"
                            },
                            org.jooq.meta.jaxb.SchemaMappingType().apply {
                                inputSchema = "security"
                            }
                        ))
                    }

                    generate.apply {
                        isRelations = true
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = false
                        isFluentSetters = true
                    }

                    target.apply {
                        packageName = "generated"
                        directory = "src/main/java"
                    }

                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}
