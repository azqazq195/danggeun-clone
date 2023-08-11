# MethodSecurityConfig

MethodSecurityConfig는 Spring Security에서 제공하는 메서드 수준의 보안 설정이다.\
아래의 어노테이션을 통해 권한을 검사할 수 있다.

## @Secured

특정 권한을 가진 사용자만 메서드에 접근할 수 있게 함

- 권한 목록만을 지원하며, SpEL(Spring Expression Language) 표현식은 지원하지 않습니다.
- 사용 예: `@Secured("ROLE_ADMIN")`

## @PreAuthorize

메서드 호출 전에 보안 표현식을 평가함

- SpEL을 사용하여 복잡한 표현식과 조건을 작성할 수 있습니다.
- 메서드 인자와 반환 값을 참조할 수 있습니다.
- `@PreAuthorize("hasRole('ROLE_ADMIN') and #user.name == authentication.name")`

## @PostAuthorize

메서드 호출 후에 보안 표현식을 평가함

- 메서드의 반환 값을 사용하여 보안 결정을 내릴 수 있습니다.
- SpEL을 사용하여 복잡한 표현식과 조건을 작성할 수 있습니다.
- `@PostAuthorize("returnObject.owner == authentication.name")`

## 요약

- @Secured: 단순한 권한 기반 제어에 사용
- @PreAuthorize: 복잡한 표현식을 평가하여 메서드 호출 전에 보안 결정
- @PostAuthorize: 메서드 호출 후 반환 값을 기반으로 보안 결정

@PreAuthorize와 @PostAuthorize는 표현식을 사용하여 복잡한 보안 요구 사항을 처리할 때 유용하고, @Secured는 간단한 권한
체크에 적합하다.

## 설정

```kotlin
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
class MethodSecurityConfig
```

prePost, secured를 사용할 지 `@Configuration` 을 통해 빈으로 등록한다.

## 표현식 종류

- hasRole([role]) : 현재 사용자의 권한이 파라미터의 권한과 동일한 경우 true
- hasAnyRole([role1,role2]) : 현재 사용자의 권한디 파라미터의 권한 중 일치하는 것이 있는 경우 true
- principal : 사용자를 증명하는 주요객체(User)를 직접 접근할 수 있다.
- authentication : SecurityContext에 있는 authentication 객체에 접근 할 수 있다.
- permitAll : 모든 접근 허용
- denyAll : 모든 접근 비허용
- isAnonymous() : 현재 사용자가 익명(비로그인)인 상태인 경우 true
- isRememberMe() : 현재 사용자가 RememberMe 사용자라면 true
- isAuthenticated() : 현재 사용자가 익명이 아니라면 (로그인 상태라면) true
- isFullyAuthenticated() : 현재 사용자가 익명이거나 RememberMe 사용자가 아니라면 true

## 추가

### WebSecurityConfig 에서 URL 패턴에 기반한 권한 검사와 동시에 쓰면 어떻게 될까?

- 첫 번째로 URL 패턴 권한 검사가 진행된다.
- 두 번째로 메서드 수준 권한 검사가 진행된다.
- 두 단계 모두 통과해야 접근이 가능하다.
- 불필요한 중복검사는 리소스 증가를 유발한다.