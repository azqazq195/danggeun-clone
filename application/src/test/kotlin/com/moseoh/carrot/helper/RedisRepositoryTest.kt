package com.moseoh.carrot.helper

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate

@DataRedisTestWithConfig
abstract class RedisRepositoryTest(
    body: BehaviorSpec.() -> Unit = {},
) : BehaviorSpec() {

    @Autowired
    @Qualifier("redisTemplate")
    private lateinit var redisTemplate: RedisTemplate<*, *>

    init {
        afterContainer {
            if (it.a.parent == null) {
                redisTemplate.connectionFactory?.connection?.commands()?.flushAll()
            }
        }

        body()
    }
}