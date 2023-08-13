package com.moseoh.carrot.helper.constraints

import com.moseoh.carrot.helper.config.TestWebSecurityConfig
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@WebMvcTest
@Import(TestWebSecurityConfig::class)
annotation class WebMvcTestWithConfig(
    @get:AliasFor(annotation = WebMvcTest::class, attribute = "value")
    val value: KClass<*> = Nothing::class
)
