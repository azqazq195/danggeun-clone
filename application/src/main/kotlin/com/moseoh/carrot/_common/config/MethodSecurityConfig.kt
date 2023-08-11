package com.moseoh.carrot._common.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class MethodSecurityConfig