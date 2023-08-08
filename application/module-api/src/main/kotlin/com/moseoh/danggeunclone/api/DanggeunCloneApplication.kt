package com.moseoh.danggeunclone.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@ComponentScan("com.moseoh.danggeunclone")
@EntityScan("com.moseoh.danggeunclone.core")
@ConfigurationPropertiesScan("com.moseoh.danggeunclone")
@EnableJpaRepositories("com.moseoh.danggeunclone")
@EnableRedisRepositories("com.moseoh.danggeunclone")
class DanggeunCloneApplication

fun main(args: Array<String>) {
    runApplication<DanggeunCloneApplication>(*args)
}

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}