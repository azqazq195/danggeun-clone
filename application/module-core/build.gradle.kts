import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-core"

plugins {
}

dependencies {
}

tasks {
    withType<Jar> {
        enabled = true
    }

    withType<BootJar> {
        enabled = false
    }
}