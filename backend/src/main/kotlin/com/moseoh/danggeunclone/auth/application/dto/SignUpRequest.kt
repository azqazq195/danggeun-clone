package com.moseoh.danggeunclone.auth.application.dto

import com.moseoh.danggeunclone.user.domain.Role
import com.moseoh.danggeunclone.user.domain.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class SignUpRequest(
    @field:NotBlank
    @field:Email(regexp = "^[\\w!#\$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#\$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}\$")
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val name: String,

    @field:Positive
    val age: Int
) {
    fun toUser(encodedPassword: String, role: Role) =
        User(
            email = email,
            password = encodedPassword,
            name = name,
            age = age,
            role = role,
        )
}