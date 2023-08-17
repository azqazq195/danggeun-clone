package com.moseoh.carrot

import com.moseoh.carrot.domain.category.entity.Category

object CategoryFixture {
    fun createCategory(
        name: String = "전자제품"
    ): Category {
        return Category(
            name
        )
    }
}