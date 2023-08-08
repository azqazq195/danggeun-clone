import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-util"

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.9.0"
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
}

// 독립적으로 실행되지 않으므로 jar 배포
tasks {
    withType<Jar> {
        enabled = true
    }

    withType<BootJar> {
        enabled = false
    }
}