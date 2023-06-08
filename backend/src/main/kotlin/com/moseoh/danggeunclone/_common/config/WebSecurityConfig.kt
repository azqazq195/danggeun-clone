package com.moseoh.danggeunclone._common.config

import com.moseoh.danggeunclone.auth.application.TokenProvider
import com.moseoh.danggeunclone.auth.infrastructure.AccessDeniedHandlerImpl
import com.moseoh.danggeunclone.auth.infrastructure.AuthenticationEntryPointImpl
import com.moseoh.danggeunclone.auth.infrastructure.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val authenticationEntryPointImpl: AuthenticationEntryPointImpl,
    private val accessDeniedHandlerImpl: AccessDeniedHandlerImpl,
    private val tokenProvider: TokenProvider,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf().disable()
            .formLogin().disable()
            .rememberMe().disable()
            .logout().disable()
            .httpBasic().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeHttpRequests()
            .requestMatchers("/health").permitAll()
            .requestMatchers("/auth/sign-in").permitAll()
            .requestMatchers("/auth/sign-up").permitAll()
            .requestMatchers("/auth/refresh").permitAll()
            .requestMatchers("/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPointImpl) // 401 에러 핸들링
            .accessDeniedHandler(accessDeniedHandlerImpl) // 403 에러 핸들링

            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(AntPathRequestMatcher("/h2-console/**"))
            web.ignoring().requestMatchers(AntPathRequestMatcher("/docs/**"))
        }
    }
}