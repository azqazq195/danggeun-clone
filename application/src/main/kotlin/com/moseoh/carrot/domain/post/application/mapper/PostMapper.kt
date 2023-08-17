package com.moseoh.carrot.domain.post.application.mapper

import com.moseoh.carrot.domain.category.entity.repository.CategoryRepository
import com.moseoh.carrot.domain.category.entity.repository.getEntityById
import com.moseoh.carrot.domain.post.application.dto.PostRequest
import com.moseoh.carrot.domain.post.entity.Post
import com.moseoh.carrot.domain.region.entity.repository.RegionRepository
import com.moseoh.carrot.domain.region.entity.repository.getEntityById
import org.springframework.stereotype.Component

@Component
class PostMapper(
    private val regionRepository: RegionRepository,
    private val categoryRepository: CategoryRepository,
) {
    fun dtoToEntity(postRequest: PostRequest): Post {
        val region = regionRepository.getEntityById(postRequest.regionId)
        val category = categoryRepository.getEntityById(postRequest.categoryId)

        return postRequest.toEntity(category, region)
    }
}