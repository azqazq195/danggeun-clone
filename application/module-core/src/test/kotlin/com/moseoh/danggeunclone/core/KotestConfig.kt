package com.moseoh.danggeunclone.core

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.SpecExtension
import io.kotest.core.spec.Spec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.data.redis.core.RedisTemplate
import kotlin.reflect.full.findAnnotation

class KotestConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(
        SpringTestExtension(SpringTestLifecycleMode.Root),
        DataRedisTestExtension()
    )

    class DataRedisTestExtension : SpecExtension {
        override suspend fun intercept(spec: Spec, execute: suspend (Spec) -> Unit) {
            if (spec::class.findAnnotation<DataRedisTest>() != null) {
                spec.afterContainer {
                    if (it.a.parent == null) {
                        val applicationContext = ApplicationContextProvider.context
                        val redisTemplate: RedisTemplate<*, *> =
                            applicationContext.getBean("redisTemplate", RedisTemplate::class.java)
                        redisTemplate.connectionFactory?.connection?.serverCommands()?.flushAll()
                    }
                }
            }
            super.intercept(spec, execute)
        }
    }
}

