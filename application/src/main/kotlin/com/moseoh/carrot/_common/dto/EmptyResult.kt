package com.moseoh.carrot._common.dto

import java.time.LocalDateTime

open class EmptyResult(
    val message: String? = null
) {
    val timestamp: LocalDateTime = LocalDateTime.now()
}