package com.moseoh.carrot.domain.post.application

import com.moseoh.carrot.CategoryFixture
import com.moseoh.carrot.PostFixture
import com.moseoh.carrot.RegionFixture
import com.moseoh.carrot.domain.category.entity.repository.CategoryRepository
import com.moseoh.carrot.domain.post.application.dto.PostDetailsResponse
import com.moseoh.carrot.domain.post.application.dto.PostSummaryResponse
import com.moseoh.carrot.domain.post.entity.repository.PostRepository
import com.moseoh.carrot.domain.region.entity.repository.RegionRepository
import com.moseoh.carrot.helper.QueryServiceTest
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

class PostQueryServiceTest @Autowired constructor(
    private val regionRepository: RegionRepository,
    private val categoryRepository: CategoryRepository,
    private val postRepository: PostRepository,
    private val postQueryService: PostQueryService
) : QueryServiceTest({
    Given("fetchOne") {
        val post = PostFixture.createPost()
        regionRepository.save(post.region)
        categoryRepository.save(post.category)
        postRepository.save(post)
        val response = post.let(::PostDetailsResponse)

        When("요청을 한다면") {
            val result = postQueryService.fetchOne(post.id)

            Then("PostDetailsResponse 를 반환한다.") {
                result shouldBe response
            }
        }
    }

    Given("search") {
        val regions = listOf(
            RegionFixture.createRegion("region1"),
            RegionFixture.createRegion("region2")
        )
        val categories = listOf(
            CategoryFixture.createCategory("category1"),
            CategoryFixture.createCategory("category2")
        )
        val posts = listOf(
            PostFixture.createPost(
                title = "post1",
                category = categories[0],
                region = regions[0],
                price = 100,
                photos = listOf(
                    PostFixture.createPhoto("url1"),
                    PostFixture.createPhoto("url2"),
                    PostFixture.createPhoto("url3"),
                    PostFixture.createPhoto("url4"),
                )
            ),
            PostFixture.createPost(
                title = "post2",
                category = categories[1],
                region = regions[1],
                price = 1000,
                photos = listOf()
            )
        )
        regionRepository.saveAll(regions)
        categoryRepository.saveAll(categories)
        postRepository.saveAll(posts)

        When("검색 조건 없이 요청을 한다면") {
            val searchParam = PostFixture.createPostSearchParam()
            val pageable = PageRequest.of(0, 10)

            val result = postQueryService.search(searchParam, pageable)

            Then("2개 반환") {
                result.size shouldBe 2
                result shouldBe posts.map(::PostSummaryResponse)
            }
        }

        When("사진이 있는 게시글만 검색 한다면") {
            val searchParam = PostFixture.createPostSearchParam(hasPhoto = true)
            val pageable = PageRequest.of(0, 10)

            val result = postQueryService.search(searchParam, pageable)

            Then("2개 반환") {
                result.size shouldBe 1
                result shouldBe posts.filter { it.photos.isNotEmpty() }.map(::PostSummaryResponse)
            }
        }

        When("category id로 검색 한다면") {
            val categoryId = categories.random().id
            val searchParam = PostFixture.createPostSearchParam(categoryId = categoryId)
            val pageable = PageRequest.of(0, 10)

            val result = postQueryService.search(searchParam, pageable)

            Then("2개 반환") {
                result.size shouldBe 1
                result shouldBe posts.filter { it.category.id == categoryId }.map(::PostSummaryResponse)
            }
        }
    }

})
