# WebSecurity Ignoring

## Authentication is Null

### 문제

로그인된 사용자 정보를 조회하는 경우 `Authentication` 에서 그 정보를 가져온다.\
이는 `JwtAutehticationFilter`에서 `SecurityContextHolder`에 값을 넣어 주는데 `null` 이 라고 오류가 발생하였다.

AuthController:

```kotlin
@RestController
@RequestMapping("/auth")
class AuthController {
    //..
    @GetMapping("/me")
    fun me(
        auth: Authentication
    ): ResponseEntity<SingleResult> {
        val user = authService.me(auth)

        return ResponseDto.of(
            message = "사용자 조회 완료.",
            status = HttpStatus.OK,
            content = user
        )
    }
    //..
}
```

JwtAuthenticationFilter:

```kotlin
@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
) : GenericFilterBean() {
    companion object {
        const val AUTH_HEADER = "Authorization"
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val token = resolveToken((request as HttpServletRequest))

        if (token != null && tokenProvider.validateToken(token)) {
            val authentication = tokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTH_HEADER)
    }
}
```

### 원인

테스트용으로 인증/인가 처리를 하지 않기 위해 `web.ignoring()` 패턴을 `/**` 으로 지정한 것이 문제였다.\
이는 해당 패턴에 대한 필터를 건너뛰기 때문에 `JwtAuthenticationFilter`이 적용되지 않아 `SecurityContextHolder`에 `authentication` 값이 들어가지 않게 된다.

```kotlin
@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    //..
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            //..
            web.ignoring().requestMatchers(AntPathRequestMatcher("/**"))
        }
    }
}
```

## WebSecurity Ignoring vs PermitAll

아래 코드에서 보면 `web.ignoring`과 `permitAll()`은 무슨 차이가 있을까?

```kotlin
@Bean
fun webSecurityCustomizer(): WebSecurityCustomizer {
    return WebSecurityCustomizer { web: WebSecurity ->
        web.ignoring().requestMatchers(AntPathRequestMatcher("/**"))
    }
}


@Bean
fun filterChain(http: HttpSecurity): SecurityFilterChain {
    return http
        .authorizeHttpRequests {
            it.requestMatchers("/**").permitAll()
        }
        .build()
}
```

- WebSecurity
    - spring security filter, security features 를 무시한다.
    - secure headers, CSRF 등도 적용되지 않는다.
- HttpSecurity
    - endpoint에 대해 인증/권한을 무시한다.
    - secure headers, CSRF 등은 적용된다.