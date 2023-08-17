package com.moseoh.carrot.domain.post.application

import com.moseoh.carrot.PostFixture
import com.moseoh.carrot.domain.post.application.mapper.PostMapper
import com.moseoh.carrot.domain.post.entity.repository.PostRepository
import com.moseoh.carrot.domain.post.entity.repository.getEntityById
import com.moseoh.carrot.domain.post.exception.PostNotFoundException
import com.moseoh.carrot.helper.ServiceTest
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired

class PostServiceTest @Autowired constructor(
    @MockkBean val postRepository: PostRepository,
    @MockkBean val postMapper: PostMapper,
    @MockK val postService: PostService,
) : ServiceTest({

    Given("create") {
        val postRequest = PostFixture.createPostRequest()
        val post = PostFixture.createPost()

        When("요청을 한다면") {
            every { postMapper.dtoToEntity(postRequest) } returns post
            every { postRepository.save(post) } returns post

            val result = postService.create(postRequest)

            Then("수행한다.") {
                result shouldBe Unit

                verify(exactly = 1) { postMapper.dtoToEntity(postRequest) }
                verify(exactly = 1) { postRepository.save(post) }
            }
        }
    }

    Given("update") {
        val postRequest = PostFixture.createPostRequest()
        val updateValue = PostFixture.createPost()
        val post = PostFixture.createPost()
        val id = post.id

        When("요청을 한다면") {
            every { postRepository.getEntityById(id) } returns post
            every { postMapper.dtoToEntity(postRequest) } returns updateValue
            every { postRepository.save(post) } returns post

            val result = postService.update(id, postRequest)

            Then("수행한다.") {
                result shouldBe Unit

                verify(exactly = 1) { postRepository.getEntityById(id) }
                verify(exactly = 1) { postMapper.dtoToEntity(postRequest) }
                verify(exactly = 1) { postRepository.save(post) }
            }
        }
    }

    Given("delete") {
        val id = 1L

        When("요청을 한다면") {
            every { postRepository.existsById(id) } returns true
            every { postRepository.deleteById(id) } just Runs

            val result = postService.delete(id)

            Then("수행한다.") {
                result shouldBe Unit

                verify(exactly = 1) { postRepository.existsById(id) }
                verify(exactly = 1) { postRepository.deleteById(id) }
            }
        }

        When("존재하지 않는 Post Id로 요청을 한다면") {
            every { postRepository.existsById(id) } returns false

            val result = shouldThrow<PostNotFoundException> {
                postService.delete(id)
            }

            Then("PostNotFoundException 이 발생한다.") {
                result shouldBe PostNotFoundException()

                verify(exactly = 1) { postRepository.existsById(id) }
            }
        }
    }
})
