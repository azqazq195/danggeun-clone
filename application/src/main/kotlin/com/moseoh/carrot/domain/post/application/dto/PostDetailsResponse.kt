package com.moseoh.carrot.domain.post.application.dto

import com.moseoh.carrot._common.utils.DateUtils
import com.moseoh.carrot.domain.category.entity.Category
import com.moseoh.carrot.domain.post.entity.Post
import com.moseoh.carrot.domain.region.entity.Region
import com.querydsl.core.annotations.QueryProjection

data class PostDetailsResponse(
    val id: Long,
    val photoUrls: List<String>,
    val title: String,
    val content: String,
    val price: Int,
    val timeAgo: String,
    val category: CategoryResponse,
    val region: RegionResponse,
) {
    @QueryProjection
    constructor(
        post: Post
    ) : this(
        id = post.id,
        photoUrls = post.photos.map { it.url },
        title = post.title,
        content = post.content,
        price = post.price,
        timeAgo = DateUtils.timeAgo(post.createdAt),
        category = post.category.let(::CategoryResponse),
        region = post.region.let(::RegionResponse),
    )

    data class CategoryResponse(
        val id: Long,
        val name: String,
    ) {
        @QueryProjection
        constructor(
            category: Category,
        ) : this(
            id = category.id,
            name = category.name,
        )
    }

    data class RegionResponse(
        val id: Long,
        val name: String,
    ) {
        @QueryProjection
        constructor(
            region: Region,
        ) : this(
            id = region.id,
            name = region.name,
        )
    }
}