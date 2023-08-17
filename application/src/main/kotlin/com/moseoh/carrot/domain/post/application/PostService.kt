package com.moseoh.carrot.domain.post.application

import com.moseoh.carrot.domain.post.application.dto.PostRequest
import com.moseoh.carrot.domain.post.application.mapper.PostMapper
import com.moseoh.carrot.domain.post.entity.repository.PostRepository
import com.moseoh.carrot.domain.post.entity.repository.getEntityById
import com.moseoh.carrot.domain.post.exception.PostNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postMapper: PostMapper,
) {
    @Transactional
    fun create(postRequest: PostRequest) {
        val post = postMapper.dtoToEntity(postRequest)
        postRepository.save(post)
    }

    @Transactional
    fun update(id: Long, postRequest: PostRequest) {
        val post = postRepository.getEntityById(id)
        val updateValue = postMapper.dtoToEntity(postRequest)

        post.update(updateValue)
        postRepository.save(post)
    }

    @Transactional
    fun delete(id: Long) {
        if (!postRepository.existsById(id)) throw PostNotFoundException()
        postRepository.deleteById(id)
    }
}
