# Rest Docs

### 이슈

index.adoc에서 작성하지 않고 파일을 분리하여 include 하였을 경우, bootJar 로 배포시 정상 표기되나 intellij 에서 Spring Boot를 실행했을 경우 include 된 adoc
파일
경로가 달라져 불러오지 못함.

### 해결

resource/static 경로에 생성된 index.html을 복사 해 옴으로써 접근 가능하다.

참조:

https://narusas.github.io/2018/03/21/Asciidoc-basic.html

---

### 이슈

```console
urlTemplate not found. If you are using MockMvc did you use RestDocumentationRequestBuilders to build the request?
java.lang.IllegalArgumentException: urlTemplate not found. If you are using MockMvc did you use
RestDocumentationRequestBuilders to build the request?
```

`rest docs` 생성 시 `pathParamters` 를 사용하면 에러가 발생했다.

### 해결

공식 문서에 `pathParamters` 사용시 `MockMvcRequestBuilders` 대신 `RestDocumentationRequestBuilders` 를 사용하라고 한다.

> To make the path parameters available for documentation, the request must be built using one of the methods on
> RestDocumentationRequestBuilders rather than MockMvcRequestBuilders.

참조:

https://docs.spring.io/spring-restdocs/docs/1.0.0.BUILD-SNAPSHOT/reference/html5/#documenting-your-api-path-parameters