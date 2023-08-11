package com.moseoh.carrot.domain.auth.application.dto


import com.moseoh.carrot.domain.auth.application.dto.constraints.EmailValidation
import jakarta.validation.constraints.Size

data class SignInRequest(
    @field:EmailValidation
    val email: String,

    @field:Size(min = 8, max = 20)
    val password: String,
)