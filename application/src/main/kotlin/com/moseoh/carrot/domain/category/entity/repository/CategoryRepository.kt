package com.moseoh.carrot.domain.category.entity.repository

import com.moseoh.carrot.domain.category.entity.Category
import com.moseoh.carrot.domain.category.exception.CategoryNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>

fun CategoryRepository.getEntityById(id: Long): Category =
    findById(id).orElseThrow(::CategoryNotFoundException)