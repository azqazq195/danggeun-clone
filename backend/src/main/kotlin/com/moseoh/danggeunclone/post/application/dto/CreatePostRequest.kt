package com.moseoh.danggeunclone.post.application.dto

import com.moseoh.danggeunclone.post.domain.*
import jakarta.validation.constraints.NotBlank

data class CreatePostRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val category: String,

    @field:NotBlank
    val content: String,
) {
    fun toPost(category: Category): Post {
        return Post(
            title = title,
            category = category,
            status = Status.SALE,
            content = content,
            address = Address("", "", ""),
            location = Location(0.0, 0.0)
        )
    }
}