package com.moseoh.danggeunclone._common.application.dto

import java.time.LocalDateTime

open class EmptyResult(
    val statusCode: Int,
    val message: String? = null
) {
    val timestamp: LocalDateTime = LocalDateTime.now()
}