package com.moseoh.danggeunclone.post.domain.repository

import com.moseoh.danggeunclone.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>
