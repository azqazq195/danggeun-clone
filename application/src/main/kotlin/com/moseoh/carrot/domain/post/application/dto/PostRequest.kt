package com.moseoh.carrot.domain.post.application.dto

import com.moseoh.carrot.domain.category.entity.Category
import com.moseoh.carrot.domain.post.entity.Photo
import com.moseoh.carrot.domain.post.entity.Post
import com.moseoh.carrot.domain.region.entity.Region
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive


data class PostRequest(
    val photoUrls: List<String>,

    @NotBlank
    val title: String,

    @NotBlank
    val content: String,

    @field:Positive
    val categoryId: Long,

    @field:Positive
    val regionId: Long,

    @field:Positive
    val price: Int,
) {
    fun toEntity(category: Category, region: Region): Post {
        return Post(
            title = title,
            content = content,
            category = category,
            region = region,
            price = price,
            photos = photoUrls.map { Photo(it) }
        )
    }
}