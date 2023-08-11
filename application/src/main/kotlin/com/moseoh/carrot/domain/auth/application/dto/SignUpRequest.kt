package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.domain.auth.application.dto.constraints.EmailValidation
import com.moseoh.carrot.domain.user.entity.Role
import com.moseoh.carrot.domain.user.entity.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @field:EmailValidation
    val email: String,

    @field:Size(min = 8, max = 20)
    val password: String,

    @field:NotBlank
    val nickname: String,
) {
    fun toEntity(encodedPassword: String): User {
        return User(
            email = email,
            password = encodedPassword,
            nickname = nickname,
            role = Role.USER,
        )
    }
}