package com.moseoh.danggeunclone.post.application

import com.moseoh.danggeunclone.post.domain.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostSearchService(
    private val postRepository: PostRepository,
) {
}