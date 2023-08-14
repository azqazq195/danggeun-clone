package com.moseoh.carrot.domain.region.entity

import com.moseoh.carrot._common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Region(
    name: String,
    parent: Region? = null,
) : BaseEntity() {
    var name: String = name
        private set

    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent: Region? = parent
        private set
}