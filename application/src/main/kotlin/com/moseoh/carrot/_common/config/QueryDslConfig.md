# 이슈

---

## querydsl 버전

Spring Boot 2.6.x 이후

- querydsl 5.0.0 사용
- querydsl 4.4.0은 지원하지 않는다.

Spring Boot 3.0.0 이전

```kotlin
implementation("com.querydsl:querydsl-jpa:5.0.0:jpa")
kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
```

Spring Boot 3.0.0 이후

```kotlin
implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
```

Spring Boot 3 버전 이후 persistent 가 변경되어 `jakarata`를 사용해야한다.

## querydsl transform

response dto 생성을 위하여 querydsl transform을 사용할 때 아래 에러가 발생하였다.

```shell
'java.lang.Object org.hibernate.ScrollableResults.get(int)'
java.lang.NoSuchMethodError: 'java.lang.Object org.hibernate.ScrollableResults.get(int)'
```

Hibernate 6.x. 버전 이상에서 `JPQLTemplates` 를 `Default`로 지정하라고 한다.\
___현재 프로젝트 기준 Spring Boot Data Jpa 3.1.1 에서 사용중인 Hibernate 버전은 6.2.5___

```kotlin
@Bean
fun jpaQueryFactory(): JPAQueryFactory {
    // return JPAQueryFactory(entityManager) <-- before
    return JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager)
}
```

참조: https://github.com/querydsl/querydsl/issues/3428
