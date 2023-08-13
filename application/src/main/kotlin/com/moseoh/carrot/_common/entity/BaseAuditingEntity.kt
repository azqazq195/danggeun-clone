package com.moseoh.carrot._common.entity

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy

@MappedSuperclass
abstract class BaseAuditingEntity : BaseTimeEntity() {
    @CreatedBy
    var createdBy: Long = 0L
        protected set

    @LastModifiedBy
    var modifiedBy: Long = 0L
        protected set
}