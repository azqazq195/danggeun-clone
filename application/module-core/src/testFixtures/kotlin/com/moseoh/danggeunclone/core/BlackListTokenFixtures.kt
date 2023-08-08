package com.moseoh.danggeunclone.core

import com.moseoh.danggeunclone.core.domain.BlackListToken

object BlackListTokenFixtures {
    fun createEntity(
        accessToken: String = "accessToken",
        expiration: Long = 1000,
    ): BlackListToken {
        return BlackListToken(
            accessToken,
            expiration,
        )
    }
}