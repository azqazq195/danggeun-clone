package com.moseoh.danggeunclone.core

import com.moseoh.danggeunclone.core.domain.Token

object TokenFixture {
    fun createEntity(
        userId: Long = 1L,
        accessToken: String = "accessToken",
        refreshToken: String = "refreshToken",
        expiration: Long = 1000,
    ): Token {
        return Token(
            userId,
            accessToken,
            refreshToken,
            expiration,
        )
    }
}