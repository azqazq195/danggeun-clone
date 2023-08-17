package com.moseoh.carrot

import com.moseoh.carrot.domain.category.entity.Category
import com.moseoh.carrot.domain.post.application.dto.PostDetailsResponse
import com.moseoh.carrot.domain.post.application.dto.PostRequest
import com.moseoh.carrot.domain.post.application.dto.PostSearchParam
import com.moseoh.carrot.domain.post.application.dto.PostSummaryResponse
import com.moseoh.carrot.domain.post.entity.Photo
import com.moseoh.carrot.domain.post.entity.Post
import com.moseoh.carrot.domain.region.entity.Region

object PostFixture {
    fun createPost(
        title: String = "제목",
        content: String = "팔아요",
        category: Category = CategoryFixture.createCategory(),
        region: Region = RegionFixture.createRegion(),
        price: Int = 10_000,
        photos: List<Photo> = listOf(
            createPhoto(),
            createPhoto(),
        ),
    ): Post {
        return Post(
            title = title,
            content = content,
            category = category,
            region = region,
            price = price,
            photos = photos,
        )
    }

    fun createPhoto(
        url: String = "url"
    ): Photo {
        return Photo(
            url = url
        )
    }

    fun createPostSearchParam(
        title: String? = null,
        content: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        hasPhoto: Boolean? = null,
        categoryId: Long? = null,
        regionId: Long? = null,
    ): PostSearchParam {
        return PostSearchParam(
            title = title,
            content = content,
            minPrice = minPrice,
            maxPrice = maxPrice,
            hasPhoto = hasPhoto,
            categoryId = categoryId,
            regionId = regionId,
        )
    }

    fun createPostRequest(
        photoUrls: List<String> = listOf("url1", "url2"),
        title: String = "title",
        content: String = "content",
        categoryId: Long = 1L,
        regionId: Long = 1L,
        price: Int = 10_000,
    ): PostRequest {
        return PostRequest(
            photoUrls = photoUrls,
            title = title,
            content = content,
            categoryId = categoryId,
            regionId = regionId,
            price = price,
        )
    }

    fun createPostDetailsResponse(
        id: Long = 1L,
        photoUrls: List<String> = listOf("url1", "url2"),
        title: String = "title",
        content: String = "content",
        price: Int = 10_000,
        category: Category = CategoryFixture.createCategory(),
        region: Region = RegionFixture.createRegion(),
        timeAgo: String = "timeAgo",
    ): PostDetailsResponse {
        return PostDetailsResponse(
            id = id,
            photoUrls = photoUrls,
            title = title,
            content = content,
            price = price,
            category = category.let(PostDetailsResponse::CategoryResponse),
            region = region.let(PostDetailsResponse::RegionResponse),
            timeAgo = timeAgo,
        )
    }

    fun createPostSummaryResponse(
        id: Long = 1L,
        photoUrl: String? = "photoUrl",
        title: String = "title",
        price: Int = 1000,
        timeAgo: String = "timeAgo",
        categoryName: String = "category",
        regionName: String = "region",
    ): PostSummaryResponse {
        return PostSummaryResponse(
            id = id,
            photoUrl = photoUrl,
            title = title,
            price = price,
            timeAgo = timeAgo,
            categoryName = categoryName,
            regionName = regionName,
        )
    }
}