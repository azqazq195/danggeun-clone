package com.moseoh.danggeunclone.support

import com.moseoh.danggeunclone.user.application.dto.UserResponse
import com.moseoh.danggeunclone.user.domain.Role
import com.moseoh.danggeunclone.user.domain.User
import java.time.LocalDateTime

fun createUser(
    email: String = "user@example.com",
    password: String = "password",
    name: String = "사용자",
    age: Int = 30,
    role: Role = Role.USER
): User {
    return User(
        email = email,
        password = password,
        name = name,
        age = age,
        role = role,
        auditing = createCreatedAuditing()
    )
}

fun createUserResponse(
    id: Long = 1,
    email: String = "user@example.com",
    role: Role = Role.USER,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now(),
): UserResponse {
    return UserResponse(
        id = id,
        email = email,
        role = role,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
    )
}