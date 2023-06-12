# Jpa Auditing

### @CreatedBy, @LastModifiedBy

해당 어노테이션은 객체 생성 및 수정 시 Entity 에 핸들링 유저 정보를 담게 된다.\
어떠한 정보를 담을지 `AuditorAware` 를 통해 커스텀 가능하다.

```kotlin
@Bean
fun auditorProvider(): AuditorAware<Long> {
    return AuditorAware<Long> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            Optional.empty()
        } else {
            Optional.of(authentication.principal as Long)
        }
    }
}
```

`auditorProvider` 빈 객체를 등록하여 사용 가능하며 반환 타입에 따라\
`@CreatedBy`, `@LastModifiedBy` 에 유저의 email, id, user 객체 등을 담을 수 있다.

`null` 처리를 하지 않으면, auditorProvider 의 빈 등록 시점에는 `authentication` 이 `null` 이기 때문에 오류가 발생한다.

### @DeletedBy

