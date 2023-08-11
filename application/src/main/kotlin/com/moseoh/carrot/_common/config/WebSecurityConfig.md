# WebSecurityConfig

## 추가

### JwtAuthenticationFilter 를 주입받지 않고 생성자를 통해 등록하는 이유

`JwtAuthenticationFilter` 를 아래와 같이 생성자를 통해 등록하고 있다.

```kotlin
@Bean
@Throws(Exception::class)
fun filterChain(http: HttpSecurity): SecurityFilterChain {
    return http
        //..
        .addFilterBefore(
            JwtAuthenticationFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .build()
}
```

다른 서비스들 처럼 `@Component` 를 붙여 주입받으면 안될까?

```kotlin
@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() 
```

`Filter` 인터페이스를 상속받은 class 를 Spring Bean으로 등록하게 되면 Spring이 ServletFilter에 등록하게 된다.\
때문에 `JwtAuthenticationFilter`를 주입받아 SecurityFilter에 등록하는 경우, ServletFilter에 한번, SecurityFilter에 한번, 총 두번 등록하게 되는
셈이다.

이 때문에 WebSecurity에서 ignore시켜도 ServletFilter에 등록되어 있기 때문에 원치않은 동작이 일어날 수 있다.

### SecurityFilter, ServletFilter

`WebSecurityConfig`의 `@EnableWebSecurity` 로 인해 추가된 SecurityFilterChain은 ServletFilter 보다 먼저 실행된다.

### UsernamePasswordAuthenticationFilter

`UsernamePasswordAuthenticationFilter`는 폼 기반 인증을 처리하는 필터로서, 로그인 요청을 가로채어 인증을 수행합니다.\
이 필터를 비활성화하면, 로그인 폼을 통한 인증 요청을 처리하지 않게 됩니다.

`UsernamePasswordAuthenticationFilter`앞에 `JwtAuthenticationFilter` 를 위치 시킴으로서 로그인 요청시 Jwt 필터를 적용하도록
하고 `.formLogin { it.disable() }`으로 기존 로그인 과정은 비활성화 시킨다.

```kotlin
@Bean
@Throws(Exception::class)
fun filterChain(http: HttpSecurity): SecurityFilterChain {
    return http
        //..
        .formLogin { it.disable() }
        //..
        .addFilterBefore(
            JwtAuthenticationFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .build()
}
```