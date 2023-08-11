package com.moseoh.carrot.helper.constraints

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.SpringBootTest

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@SpringBootTest
@ConfigurationPropertiesScan("com.moseoh.carrot._common.environment")
annotation class SpringBootTestWithConfig