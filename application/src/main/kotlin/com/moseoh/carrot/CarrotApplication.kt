package com.moseoh.carrot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarrotApplication

fun main(args: Array<String>) {
    runApplication<CarrotApplication>(*args)
}
