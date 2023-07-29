package com.moseoh.danggeunclone.core.common.domain

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseTimeEntity : BaseEntity() {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.now()
        protected set
}