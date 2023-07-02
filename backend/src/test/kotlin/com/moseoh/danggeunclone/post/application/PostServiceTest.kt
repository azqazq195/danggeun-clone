package com.moseoh.danggeunclone.post.application

import com.moseoh.danggeunclone._common.utils.AuthUtils
import com.moseoh.danggeunclone.post.application.dto.PostResponse
import com.moseoh.danggeunclone.post.domain.repository.CategoryRepository
import com.moseoh.danggeunclone.post.domain.repository.PostRepository
import com.moseoh.danggeunclone.post.domain.repository.getByName
import com.moseoh.danggeunclone.post.exception.NotFoundCategoryException
import com.moseoh.danggeunclone.post.exception.NotFoundPostException
import com.moseoh.danggeunclone.support.createCategory
import com.moseoh.danggeunclone.support.createCreatePostRequest
import com.moseoh.danggeunclone.support.createPost
import com.moseoh.danggeunclone.support.createUpdatePostRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.util.*

internal class PostServiceTest : BehaviorSpec({
    val postRepository = mockk<PostRepository>()
    val categoryRepository = mockk<CategoryRepository>()
    val postService = PostService(postRepository, categoryRepository)
    mockkObject(AuthUtils)

    afterTest {
        clearAllMocks()
    }

    Given("get") {
        val post = createPost()
        val postResponse = post.let(::PostResponse)

        When("요청을 한다면") {
            every { postRepository.findById(post.id) } returns Optional.of(post)

            val result = postService.get(post.id)

            Then("조회한다.") {
                result shouldBe postResponse

                verify(exactly = 1) { postRepository.findById(post.id) }
            }
        }

        When("post 가 존재하지 않는다면") {
            every { postRepository.findById(post.id) } returns Optional.empty()

            val exception = shouldThrow<NotFoundPostException> {
                postService.get(post.id)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe NotFoundPostException().message

                verify(exactly = 1) { postRepository.findById(post.id) }
            }
        }
    }

    Given("create") {
        val createPostRequest = createCreatePostRequest()
        val post = createPost()
        val postResponse = post.let(::PostResponse)

        When("요청을 한다면") {
            every { categoryRepository.getByName(createPostRequest.category) } returns post.category
            every { postRepository.save(any()) } returns post

            val result = postService.create(createPostRequest)

            Then("생성한다.") {
                result shouldBe postResponse

                verify(exactly = 1) { categoryRepository.getByName(createPostRequest.category) }
                verify(exactly = 1) { postRepository.save(any()) }
            }
        }

        When("카테고리가 없다면") {
            every { categoryRepository.getByName(createPostRequest.category) } throws NotFoundCategoryException()

            val exception = shouldThrow<NotFoundCategoryException> {
                postService.create(createPostRequest)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe NotFoundCategoryException().message

                verify(exactly = 1) { categoryRepository.getByName(createPostRequest.category) }
                verify(exactly = 0) { postRepository.save(any()) }
            }
        }
    }

    Given("update") {
        val post = createPost()
        val id = post.id
        val updatePostRequest = createUpdatePostRequest(title = "update title", category = "update category")
        val updatedPost = post.updated(updatePostRequest, createCategory(name = "update category"))
        val response = updatedPost.let(::PostResponse)

        When("요청을 한다면") {
            every { postRepository.findById(id) } returns Optional.of(post)
            every { categoryRepository.getByName(updatePostRequest.category) } returns updatedPost.category
            every { postRepository.save(updatedPost) } returns updatedPost
            every { AuthUtils.loginUserId } returns post.auditing.createdBy!!

            val result = postService.update(id, updatePostRequest)

            Then("수정한다.") {
                result shouldBe response

                verify(exactly = 1) { postRepository.findById(id) }
                verify(exactly = 1) { categoryRepository.getByName(updatePostRequest.category) }
                verify(exactly = 1) { postRepository.save(updatedPost) }
            }
        }

        When("post 가 존재하지 않는다면") {
            every { postRepository.findById(id) } returns Optional.empty()

            val exception = shouldThrow<NotFoundPostException> {
                postService.update(id, updatePostRequest)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe NotFoundPostException().message

                verify(exactly = 1) { postRepository.findById(id) }
                verify(exactly = 0) { categoryRepository.getByName(updatePostRequest.category) }
                verify(exactly = 0) { postRepository.save(updatedPost) }
            }
        }

        When("작성자가 아니라면") {
            every { postRepository.findById(id) } returns Optional.of(post)
            every { AuthUtils.loginUserId } returns 0

            val exception = shouldThrow<IllegalStateException> {
                postService.update(id, updatePostRequest)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe "게시글 작성자가 아닙니다."

                verify(exactly = 1) { postRepository.findById(id) }
                verify(exactly = 0) { categoryRepository.getByName(updatePostRequest.category) }
                verify(exactly = 0) { postRepository.save(updatedPost) }
            }
        }
    }

    Given("delete") {
        val post = createPost()
        val id = post.id

        When("요청을 한다면") {
            every { postRepository.findById(id) } returns Optional.of(post)
            every { AuthUtils.loginUserId } returns 1
            every { postRepository.delete(post) } just runs

            postService.delete(id)

            Then("삭제한다.") {
                verify(exactly = 1) { postRepository.findById(id) }
                verify(exactly = 1) { postRepository.delete(post) }
            }
        }

        When("post 가 존재하지 않는다면") {
            every { postRepository.findById(id) } returns Optional.empty()

            val exception = shouldThrow<NotFoundPostException> {
                postService.delete(id)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe NotFoundPostException().message

                verify(exactly = 1) { postRepository.findById(id) }
                verify(exactly = 0) { postRepository.delete(post) }
            }
        }

        When("작성자가 아니라면") {
            every { postRepository.findById(id) } returns Optional.of(post)
            every { AuthUtils.loginUserId } returns 0

            val exception = shouldThrow<IllegalStateException> {
                postService.delete(id)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe "게시글 작성자가 아닙니다."

                verify(exactly = 1) { postRepository.findById(id) }
                verify(exactly = 0) { postRepository.delete(post) }
            }
        }
    }
})