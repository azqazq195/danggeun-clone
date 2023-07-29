import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-auth"

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("plugin.spring") version "1.9.0"
}

dependencies {
    // module
    implementation(project(":module-core"))

    // spring security
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // test
    testImplementation("org.springframework.security:spring-security-test")
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