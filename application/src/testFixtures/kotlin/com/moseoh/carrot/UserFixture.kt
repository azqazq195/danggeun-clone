package com.moseoh.carrot

import com.moseoh.carrot.domain.user.entity.Region
import com.moseoh.carrot.domain.user.entity.Role
import com.moseoh.carrot.domain.user.entity.User

object UserFixture {
    fun createUser(
        role: Role = Role.USER,
        email: String = "email",
        password: String = "password",
        nickname: String = "nickname",
        phone: String = "01012345678",
        profileUrl: String? = "url",
        regions: List<Region> = listOf(createRegion(), createRegion("도곡동")),
    ): User {
        return User(
            role,
            email,
            password,
            nickname,
            phone,
            profileUrl,
            regions,
        )
    }

    fun createRegion(
        name: String = "삼평동"
    ): Region {
        return Region(
            name
        )
    }
}