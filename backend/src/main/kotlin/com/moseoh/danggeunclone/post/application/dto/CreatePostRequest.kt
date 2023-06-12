package com.moseoh.danggeunclone.post.application.dto

import com.moseoh.danggeunclone.post.domain.*

data class CreatePostRequest(
    val title: String,
    val category: String,
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