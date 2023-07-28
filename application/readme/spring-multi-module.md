# 멀티 모듈

## 멀티 모듈의 필요성

## 멀티 모듈 구성하기

kotlin gradlew 모듈은 2023-07-27 기준으로 비활성화 되어있다고 한다.\
이 때문에 gradle-java 로 모듈을 만들고 kotlin 설정을 해주었다.

참조:\
https://youtrack.jetbrains.com/issue/IDEA-296699/New-module-wizard-There-is-no-option-to-create-Kotlin-module

### 1. 모듈 생성

gradle-java로 모듈을 생성한다.

![module1](assets/module1.png)

### 2. kotlin 폴더 추가

java로 생성된 소스 폴더 그래도 kotlin을 만들어준다.

![module2](assets/module2.png)

### 3. 소스 폴더 지정

기존 java로 지정된 소스 폴더를 kotlin으로 바꿔준다.\
test 폴더도 동일하게 바꿔준다.

![module3](assets/module3.png)

## Dependency

### api vs implementation

이 둘의 차이는 무엇일까?

module-api <- module-core 의 종속성을 갖을 때, module-core에서 종속성을 설정하는 방법에 따라 아래의 경우로 나누니다.

- api
    - api의 모듈이 core 모듈의 dependency를 사용할 수 있게 한다.
- implementation
    - api의 모듈이 core 모듈의 dependency를 사용할 수 없다. core에서만 종속됨.

