package com.moseoh.danggeunclone.post.domain.repository

import com.moseoh.danggeunclone.post.domain.Category
import com.moseoh.danggeunclone.post.exception.NotFoundCategoryException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?
}

fun CategoryRepository.getByName(name: String): Category = findByName(name) ?: throw NotFoundCategoryException()