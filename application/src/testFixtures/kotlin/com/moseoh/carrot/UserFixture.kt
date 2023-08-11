package com.moseoh.carrot

import com.moseoh.carrot.domain.user.entity.Role
import com.moseoh.carrot.domain.user.entity.User

object UserFixture {
    fun createUser(
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