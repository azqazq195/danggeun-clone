package com.moseoh.danggeunclone._common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class AuditingConfig {
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
}
