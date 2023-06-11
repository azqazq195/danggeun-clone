package com.moseoh.danggeunclone._common.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class DeletableEntity : ModifiableEntity() {
    @Column(nullable = true, updatable = true)
    var deletedAt: LocalDateTime? = null
        private set
}
