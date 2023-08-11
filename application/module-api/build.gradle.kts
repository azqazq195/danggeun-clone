description = "module-api"

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("plugin.spring") version "1.9.0"
}

dependencies {
    // module 추가
    implementation(project(":module-util"))
    implementation(project(":module-common"))
    implementation(project(":module-core"))
    implementation(project(":module-auth"))

    // spring web
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // TODO Extension Function 작성시 필요할까?
    // repository에서 custom method를 사용하기 위해 필요하다.
    // module-core에서 이부분을 api로 변경하여 상위 모듈에 전파할 수도 있다.
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
