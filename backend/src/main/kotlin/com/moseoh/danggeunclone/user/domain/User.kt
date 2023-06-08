package com.moseoh.danggeunclone.user.domain

import com.moseoh.danggeunclone._common.domain.DeletableEntity
import jakarta.persistence.*
import org.springframework.util.ObjectUtils

@Entity(name = "tb_user")
data class User(
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

    ) : DeletableEntity() {
    fun updatedName(name: String): User {
        return if (ObjectUtils.isEmpty(name)) this
        else this.copy(name = name)
    }
}