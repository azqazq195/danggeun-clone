package com.moseoh.carrot.domain.user.entity

import com.moseoh.carrot._common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class User(
    email: String,
    password: String,
    nickname: String,
    role: Role = Role.USER,
) : BaseTimeEntity() {
    @Column(unique = true)
    var email: String = email
        private set

    @Column(nullable = false)
    var password: String = password
        private set

    @Column(nullable = false)
    var nickname: String = nickname
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = role
        private set
}