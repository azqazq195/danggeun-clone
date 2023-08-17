package com.moseoh.carrot.domain.category.entity

import com.moseoh.carrot._common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Category(
    name: String,
    parent: Category? = null,
) : BaseEntity() {
    var name: String = name
        private set

    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent: Category? = parent
        private set
}