package com.moseoh.danggeunclone.support

import com.moseoh.danggeunclone._common.domain.AuditingEntity
import com.moseoh.danggeunclone.post.application.dto.CreatePostRequest
import com.moseoh.danggeunclone.post.application.dto.PostResponse
import com.moseoh.danggeunclone.post.application.dto.UpdatePostRequest
import com.moseoh.danggeunclone.post.domain.*
import java.time.LocalDateTime

fun createPost(
    id: Long = 1L,
    title: String = "title",
    category: Category = createCategory(),
    status: Status = Status.SALE,
    content: String = "content",
    address: Address = createAddress(),
    location: Location = createLocation(),
    auditing: AuditingEntity = createCreatedAuditing(),
): Post {
    return Post(
        id,
        title,
        category,
        status,
        content,
        address,
        location,
        auditing,
    )
}

fun createCategory(
    id: Long = 1L,
    name: String = "category"
): Category {
    return Category(
        id,
        name
    )
}

fun createAddress(
    state: String = "state",
    city: String = "city",
    town: String = "town",
): Address {
    return Address(
        state,
        city,
        town,
    )
}

fun createLocation(
    longitude: Double = 30.0,
    latitude: Double = 30.0,
): Location {
    return Location(
        longitude,
        latitude,
    )
}

fun createPostResponse(
    id: Long = 1L,
    title: String = "post",
    category: String = "category",
    status: String = Status.SALE.name,
    content: String = "content",
    address: Address = createAddress(),
    location: Location = createLocation(),
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedAt: LocalDateTime = LocalDateTime.now(),
): PostResponse {
    return PostResponse(
        id,
        title,
        category,
        status,
        content,
        address,
        location,
        createdAt,
        modifiedAt
    )
}

fun createCreatePostRequest(
    title: String = "title",
    category: String = "category",
    content: String = "content",
): CreatePostRequest {
    return CreatePostRequest(
        title,
        category,
        content
    )
}

fun createUpdatePostRequest(
    title: String = "title",
    category: String = "category",
    content: String = "content",
    status: Status = Status.SALE,
): UpdatePostRequest {
    return UpdatePostRequest(
        title,
        category,
        content,
        status
    )
}