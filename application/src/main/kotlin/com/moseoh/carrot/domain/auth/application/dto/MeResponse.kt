package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.domain.user.entity.Role
import com.moseoh.carrot.domain.user.entity.User
import java.time.LocalDateTime

data class MeResponse(
    val id: Long,
    val role: Role,
    val email: String,
    val nickname: String,
    val phone: String,
    val profileUrl: String?,
    val temperature: Double,
    val regions: List<String>,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    constructor(user: User) : this(
        id = user.id,
        role = user.role,
        email = user.email,
        nickname = user.nickname,
        phone = user.phone,
        profileUrl = user.profileUrl,
        temperature = user.temperature,
        regions = user.regions.map { it.name },
        createdAt = user.createdAt,
        modifiedAt = user.modifiedAt
    )
}