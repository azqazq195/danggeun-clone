package com.moseoh.danggeunclone._common.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class RedisDao(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val redisBlackListTemplate: RedisTemplate<String, String>
) {
    // refresh token handling
    fun set(key: String, value: Any, duration: Duration) {
        redisTemplate.opsForValue().set(key, value, duration)
    }

    fun get(key: String): Optional<Any> {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }

    // access token handling
    fun setBlackList(key: String, value: String, duration: Duration) {
        redisBlackListTemplate.opsForValue().set(key, value, duration)
    }

    fun hasKeyInBlackList(key: String): Boolean {
        return redisBlackListTemplate.hasKey(key)
    }
}