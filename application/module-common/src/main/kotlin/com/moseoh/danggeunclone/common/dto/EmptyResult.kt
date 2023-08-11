package com.moseoh.danggeunclone.common.dto

import java.time.LocalDateTime

open class EmptyResult(
    val message: String? = null
) {
    val timestamp: LocalDateTime = LocalDateTime.now()
}