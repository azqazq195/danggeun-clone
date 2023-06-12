package com.moseoh.danggeunclone.user.domain

import com.moseoh.danggeunclone._common.domain.AuditingEntity
import jakarta.persistence.*

@Entity(name = "tb_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 20, nullable = false, unique = true)
    val email: String,

    @Column(length = 255, nullable = false)
    val password: String,

    @Column(length = 10, nullable = false)
    val name: String,

    @Column(nullable = true)
    val age: Int? = null,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Embedded
    val auditing: AuditingEntity = AuditingEntity(),
)