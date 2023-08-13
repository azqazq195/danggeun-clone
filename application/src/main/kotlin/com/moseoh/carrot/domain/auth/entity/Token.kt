package com.moseoh.carrot.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.util.concurrent.TimeUnit

@RedisHash
class Token(
    userId: Long,
    accessToken: String,
    refreshToken: String,
    expiration: Long,
) {
    // @jakarta.persistence.Id 는 jpa 용이다.
    // @org.springframework.data.annotation.Id 를 사용하자
    @Id
    var userId: Long = userId
        private set

    @Indexed
    var accessToken: String = accessToken
        private set

    // @Indexed 하지 않으면 key값으로 활용할 수 없다.
    @Indexed
    var refreshToken: String = refreshToken
        private set

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    var expiration: Long = expiration
        private set
}