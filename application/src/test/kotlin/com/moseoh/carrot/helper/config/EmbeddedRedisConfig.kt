package com.moseoh.carrot.helper.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import redis.embedded.RedisServer

class EmbeddedRedisConfig {
    private val redisServer: RedisServer = RedisServer()

    @PostConstruct
    fun startRedis() {
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}