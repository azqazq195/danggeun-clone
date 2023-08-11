package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.domain.user.entity.Role
import com.moseoh.carrot.domain.user.entity.User
import java.time.LocalDateTime

data class MeResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        nickname = user.nickname,
        role = user.role,
        createdAt = user.createdAt,
        modifiedAt = user.modifiedAt
    )
}