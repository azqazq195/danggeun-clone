package com.moseoh.carrot.domain.post.entity

import com.moseoh.carrot._common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Photo(
    url: String
) : BaseEntity() {
    @Column(nullable = false)
    var url: String = url
        private set
}