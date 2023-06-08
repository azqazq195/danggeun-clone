package com.moseoh.danggeunclone.user.application.dto

import com.moseoh.danggeunclone.user.domain.Role
import com.moseoh.danggeunclone.user.domain.User
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        role = user.role,
        createdAt = user.createdAt!!,
        modifiedAt = user.modifiedAt!!
    )
}