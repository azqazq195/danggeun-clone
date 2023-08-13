package com.moseoh.carrot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.moseoh.carrot._common.environment")
class CarrotApplication

fun main(args: Array<String>) {
    runApplication<CarrotApplication>(*args)
}
