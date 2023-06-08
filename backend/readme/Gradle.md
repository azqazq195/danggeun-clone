```kotlin
tasks {
    // ...

    test {
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
```

### test

- `useJUnitPlatform`: JUnitPlatform 을 사용
- `outputs.dir(snippetsDir)`: outputs.dir 를 snippetsDir 로 지정함

### asciidoctor

- `dependsOn(test)`: `asciidoctor` 이전에 `test` 를 실행시킨다.\
  test를 진행해야 adoc 파일을 생성함으로 restdocs html을 만들기 전에 test를 수행하야 함.
- `inputs.dir(snippetsDir)`: `asciidoctor`가 작업할 폴더를 test에서 생성된 파일로 지정한다.
- `configurations(asciidoctorExt.name)`: 작업 설정을
  지정한다. `asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")`
- `baseDirFollowsSourceFile()`: `src/docs/asciidoc` 에서 `adoc` 파일을 경로 포함하여 작성할 경우 file 경로를 맞춰주기 위해 사용한다.

### build

- `dependsOn(asciidoctor)`: `build` 이전에 `asciidoctor` 를 실행시킨다.
- `copy`: 결과물(`from`)들을 지정한 폴더(`into`)로 복사시킨다.
- 이 작업은 로컬환경 서버 구동 시 static 경로를 통해 해당 파일을 조회하기 위함이다.

### bootJar

- bootJar을 통해 jar을 배포하더라도 별도의 설정없이 docs에 접근 가능하다.
- bootJar 전에 build가 실행되기 때문에 static 폴더에 이미 복사된 상태이다.