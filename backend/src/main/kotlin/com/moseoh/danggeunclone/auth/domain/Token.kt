package com.moseoh.danggeunclone.auth.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Token(
    @Id
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: Date,
)