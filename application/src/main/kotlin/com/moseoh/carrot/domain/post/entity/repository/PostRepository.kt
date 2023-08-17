package com.moseoh.carrot.domain.post.entity.repository

import com.moseoh.carrot.domain.post.entity.Post
import com.moseoh.carrot.domain.post.exception.PostNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>

fun PostRepository.getEntityById(id: Long): Post =
    findById(id).orElseThrow(::PostNotFoundException)