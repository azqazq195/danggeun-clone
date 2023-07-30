package com.moseoh.danggeunclone.auth.environment

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val refreshSecret: String,
)