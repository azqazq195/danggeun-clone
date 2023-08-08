package com.moseoh.danggeunclone.core.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.util.concurrent.TimeUnit

@RedisHash
class BlackListToken(
    accessToken: String,
    expiration: Long
) {
    @Id
    @Indexed
    var accessToken: String = accessToken
        private set

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    var expiration: Long = expiration
        private set

    constructor(token: Token) : this(
        token.accessToken,
        token.expiration
    )
}