package com.moseoh.carrot.domain.post.application.dto

data class PostSearchParam(
    val title: String?,
    val content: String?,
    val minPrice: Int?,
    val maxPrice: Int?,
    val hasPhoto: Boolean?,
    val categoryId: Long?,
    val regionId: Long?,
)