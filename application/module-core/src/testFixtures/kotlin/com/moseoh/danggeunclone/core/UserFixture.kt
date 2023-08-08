package com.moseoh.danggeunclone.core

import com.moseoh.danggeunclone.core.domain.Role
import com.moseoh.danggeunclone.core.domain.User

object UserFixture {
    fun createEntity(
        email: String = "email",
        password: String = "password",
        nickname: String = "nickname",
        role: Role = Role.USER
    ): User {
        return User(
            email,
            password,
            nickname,
            role
        )
    }
}