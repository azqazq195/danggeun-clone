package com.moseoh.danggeunclone.post.application.dto

import com.moseoh.danggeunclone.post.domain.Status

data class UpdatePostRequest(
    val title: String?,
    val category: String?,
    val content: String?,
    val status: Status?
)