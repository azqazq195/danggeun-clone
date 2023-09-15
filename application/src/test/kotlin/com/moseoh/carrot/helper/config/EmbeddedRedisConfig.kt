package com.moseoh.carrot.helper.config

import io.lettuce.core.RedisURI
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.core.io.ClassPathResource
import redis.embedded.RedisServer
import java.io.File

class EmbeddedRedisConfig {
    private val redisServer: RedisServer =
        if (isArmMac()) RedisServer(
            getRedisFileForArmMac(),
            RedisURI.DEFAULT_REDIS_PORT
        ) else RedisServer()

    @PostConstruct
    fun startRedis() {
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }

    private fun isArmMac(): Boolean {
        return System.getProperty("os.arch") == "aarch64" &&
                System.getProperty("os.name") == "Mac OS X"
    }

    private fun getRedisFileForArmMac(): File {
        return ClassPathResource("redis-server-6.2.13-mac-arm64").file
    }
}