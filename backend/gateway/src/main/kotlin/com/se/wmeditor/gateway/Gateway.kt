package com.se.wmeditor.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient


@SpringBootApplication
@EnableEurekaClient
class Gateway

fun main(args: Array<String>) {
    SpringApplication(Gateway::class.java).apply {
        addInitializers(beans())
        run(*args)
    }
}