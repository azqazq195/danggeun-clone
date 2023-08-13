package com.moseoh.carrot.helper.constraints

import com.moseoh.carrot.helper.config.EmbeddedRedisConfig
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@DataRedisTest
@Import(EmbeddedRedisConfig::class)
annotation class DataRedisTestWithConfig
