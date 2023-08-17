package com.moseoh.carrot

import com.moseoh.carrot.domain.region.entity.Region
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
        regions: List<Region> = listOf(RegionFixture.createRegion(), RegionFixture.createRegion("도곡동")),
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
}