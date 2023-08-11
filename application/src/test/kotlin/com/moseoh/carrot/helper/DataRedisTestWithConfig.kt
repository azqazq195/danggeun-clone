package com.moseoh.carrot.helper

import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@DataRedisTest
@Import(EmbeddedRedisConfig::class)
annotation class DataRedisTestWithConfig
