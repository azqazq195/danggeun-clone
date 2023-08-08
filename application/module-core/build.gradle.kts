import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-core"

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("java-test-fixtures")
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
}

val kotestVersion = "5.6.2"

dependencies {
    // spring jpa
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // database
    implementation("mysql:mysql-connector-java:8.0.32")
    testImplementation("com.h2database:h2:2.2.220")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("it.ozimov:embedded-redis:0.7.3") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
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