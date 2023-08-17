package com.moseoh.carrot.domain.post.application.dto

import com.moseoh.carrot._common.utils.DateUtils
import com.moseoh.carrot.domain.post.entity.Post
import com.querydsl.core.annotations.QueryProjection

data class PostSummaryResponse(
    val id: Long,
    val photoUrl: String?,
    val title: String,
    val price: Int,
    val timeAgo: String,
    val categoryName: String,
    val regionName: String,
) {
    @QueryProjection
    constructor(
        post: Post
    ) : this(
        id = post.id,
        photoUrl = post.photos.firstOrNull()?.url,
        title = post.title,
        price = post.price,
        timeAgo = DateUtils.timeAgo(post.createdAt),
        categoryName = post.category.name,
        regionName = post.region.name,
    )
}