package com.moseoh.danggeunclone.core

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import redis.embedded.RedisServer

@TestConfiguration
@SpringBootApplication
class TestConfiguration {
    @Bean
    fun applicationContextProvider() = ApplicationContextProvider()
}

class ApplicationContextProvider : ApplicationContextAware {
    companion object {
        lateinit var context: ApplicationContext
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}

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