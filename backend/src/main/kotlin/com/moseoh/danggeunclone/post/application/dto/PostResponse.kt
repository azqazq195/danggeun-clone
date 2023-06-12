package com.moseoh.danggeunclone.post.application.dto

import com.moseoh.danggeunclone.post.domain.Address
import com.moseoh.danggeunclone.post.domain.Location
import com.moseoh.danggeunclone.post.domain.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val category: String,
    val status: String,
    val content: String,
    val address: Address,
    val location: Location,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
    constructor(post: Post) : this(
        post.id,
        post.title,
        post.category.name,
        post.status.name,
        post.content,
        post.address,
        post.location,
        post.auditing.createdAt!!,
        post.auditing.modifiedAt!!,
    )
}