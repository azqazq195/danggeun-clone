package com.moseoh.carrot.domain.auth.application.dto

import jakarta.validation.constraints.NotBlank

data class TokenRequest(
    @field:NotBlank
    val accessToken: String,

    @field:NotBlank
    val refreshToken: String
)