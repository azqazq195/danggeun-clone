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
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .rememberMe { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers("/health").permitAll()
                it.requestMatchers("/health").permitAll()
                it.requestMatchers("/auth/sign-in").permitAll()
                it.requestMatchers("/auth/sign-up").permitAll()
                it.requestMatchers("/auth/refresh").permitAll()
                it.requestMatchers("/**").hasAnyRole("USER", "ADMIN")

                it.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPointImpl)
                it.accessDeniedHandler(accessDeniedHandlerImpl)
            }
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