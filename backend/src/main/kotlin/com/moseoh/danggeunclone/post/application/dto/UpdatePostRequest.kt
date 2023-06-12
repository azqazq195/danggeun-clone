package com.moseoh.danggeunclone.post.application.dto

import com.moseoh.danggeunclone.post.domain.Status
import jakarta.validation.constraints.NotBlank

data class UpdatePostRequest(
    @field:NotBlank
    val title: String?,

    @field:NotBlank
    val category: String?,

    @field:NotBlank
    val content: String?,

    @field:NotBlank
    val status: Status?
)