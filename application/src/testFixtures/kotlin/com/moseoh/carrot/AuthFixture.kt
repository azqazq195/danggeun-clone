package com.moseoh.carrot

import com.moseoh.carrot.domain.auth.entity.BlackListToken
import com.moseoh.carrot.domain.auth.entity.Token

object AuthFixture {
    fun createToken(
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

    fun createBlackListToken(
        accessToken: String = "accessToken",
        expiration: Long = 1000,
    ): BlackListToken {
        return BlackListToken(
            accessToken,
            expiration,
        )
    }
}