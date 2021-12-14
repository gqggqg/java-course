application {
    mainClass.set("admin.AdministratorLauncher")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "admin.AdministratorLauncher"
    }
}
