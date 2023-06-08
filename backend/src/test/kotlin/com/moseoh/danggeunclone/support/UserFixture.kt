package com.moseoh.danggeunclone.support

import com.moseoh.danggeunclone.user.application.dto.UserResponse
import com.moseoh.danggeunclone.user.domain.Role
import com.moseoh.danggeunclone.user.domain.User
import java.time.LocalDateTime

fun createUser(role: Role = Role.USER): User {
    return User(
        email = "${role.name.lowercase()}@example.com",
        password = "password",
        name = "사용자",
        age = 30,
        role = role,
    )
}

fun createUserResponse(): UserResponse {
    return UserResponse(
        id = 1,
        email = "user@example.com",
        role = Role.USER,
        createdAt = LocalDateTime.now(),
        modifiedAt = LocalDateTime.now(),
    )
}