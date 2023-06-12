package com.moseoh.danggeunclone._common.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Embeddable
class AuditingEntity {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    var createdBy: Long? = null
        private set

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
        private set

    @LastModifiedBy
    @Column(nullable = false, updatable = true)
    var modifiedBy: Long? = null
        private set

    @LastModifiedDate
    @Column(nullable = false, updatable = true)
    var modifiedAt: LocalDateTime? = null
        private set

    @Column(nullable = true, updatable = true)
    var deletedAt: LocalDateTime? = null
        private set
}