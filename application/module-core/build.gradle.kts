import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-core"

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
}

dependencies {
    // spring jpa
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // database
    implementation("mysql:mysql-connector-java:8.0.32")
    testImplementation("com.h2database:h2:2.2.220")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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