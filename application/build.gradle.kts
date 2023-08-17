import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("java-test-fixtures")
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
    kotlin("kapt") version "1.9.0"
}

group = "com.moseoh"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val asciidoctorExt: Configuration by configurations.creating
val snippetsDir by extra { "build/generated-snippets" }

dependencies {
    val querydslVersion = "5.0.0"
    val kotestVersion = "5.6.2"
    val jwtVersion = "0.11.5"

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    // database
    implementation("mysql:mysql-connector-java:8.0.32")

    /**
     * TEST Dependencies
     */

    // spring
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito")
    }
    testImplementation("org.springframework.security:spring-security-test")


    // mockk
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    // rest docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    // database
    testImplementation("com.h2database:h2:2.2.220")
    testImplementation("it.ozimov:embedded-redis:0.7.3") {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }

    /**
     * TEST FIXTURES Dependencies
     */
    // test fixtures directory 에서 spring security 를 사용하기 위함 `testFixturesImplementation`
    testFixturesImplementation("org.springframework.security:spring-security-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    withType<Test> {
        useJUnitPlatform()
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        dependsOn(test)
        inputs.dir(snippetsDir)
        configurations(asciidoctorExt.name)
        baseDirFollowsSourceFile()
    }

    build {
        dependsOn(asciidoctor)
        copy {
            from("${asciidoctor.get().outputDir}")
            into("src/main/resources/static/docs")
        }
    }
}