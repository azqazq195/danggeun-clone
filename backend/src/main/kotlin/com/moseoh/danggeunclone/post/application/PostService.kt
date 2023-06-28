package com.moseoh.danggeunclone.post.application

import com.moseoh.danggeunclone._common.utils.AuthUtils
import com.moseoh.danggeunclone.post.application.dto.CreatePostRequest
import com.moseoh.danggeunclone.post.application.dto.PostResponse
import com.moseoh.danggeunclone.post.application.dto.UpdatePostRequest
import com.moseoh.danggeunclone.post.domain.Post
import com.moseoh.danggeunclone.post.domain.repository.CategoryRepository
import com.moseoh.danggeunclone.post.domain.repository.PostRepository
import com.moseoh.danggeunclone.post.domain.repository.getByName
import com.moseoh.danggeunclone.post.exception.NotFoundPostException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional(readOnly = true)
    fun get(id: Long): PostResponse {
        val post = postRepository.findById(id).orElseThrow(::NotFoundPostException)
        return post.let(::PostResponse)
    }

    @Transactional
    fun create(createPostRequest: CreatePostRequest): PostResponse {
        val category = categoryRepository.getByName(createPostRequest.category)
        val post = createPostRequest.toPost(category)
        return postRepository.save(post).let(::PostResponse)
    }

    @Transactional
    fun update(id: Long, updatePostRequest: UpdatePostRequest): PostResponse {
        val post = postRepository.findById(id).orElseThrow(::NotFoundPostException)
        checkOwner(post)
        val updatedPost = post.updated(
            updatePostRequest,
            updatePostRequest.category?.let { categoryRepository.getByName(it) }
        )
        return postRepository.save(updatedPost).let(::PostResponse)
    }

    @Transactional
    fun delete(id: Long) {
        val post = postRepository.findById(id).orElseThrow(::NotFoundPostException)
        checkOwner(post)
        postRepository.delete(post)
    }

    private fun checkOwner(post: Post) {
        check(post.auditing.createdBy == AuthUtils.loginUserId) { "게시글 작성자가 아닙니다." }
    }
}