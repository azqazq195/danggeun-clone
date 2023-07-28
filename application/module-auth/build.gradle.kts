import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-auth"

plugins {
}

dependencies {
    api(project(":module-core"))
}

tasks {
    withType<Jar> {
        enabled = true
    }

    withType<BootJar> {
        enabled = false
    }
}