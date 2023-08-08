import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "module-auth"

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
}

val kotestVersion = "5.6.2"
val jwtVersion = "0.11.5"

dependencies {
    // module
    implementation(project(":module-util"))
    implementation(project(":module-common"))
    implementation(project(":module-core"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // repository에서 custom method를 사용하기 위해 필요하다.
    // module-core에서 이부분을 api로 변경하여 상위 모듈에 전파할 수도 있다.
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test:6.0.3")

//    {
//        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
//        exclude(group = "org.mockito")
//    }
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
//    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("com.h2database:h2:2.2.220")
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