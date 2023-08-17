package com.moseoh.carrot._common.utils

import java.time.Duration
import java.time.LocalDateTime

object DateUtils {
    fun timeAgo(createdAt: LocalDateTime): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(createdAt, now)

        return when {
            duration.toDays() > 0 -> "${duration.toDays()}일 전"
            duration.toHours() > 0 -> "${duration.toHours()}시간 전"
            duration.toMinutes() > 0 -> "${duration.toMinutes()}분 전"
            else -> "방금 전"
        }
    }
}