package com.se.wmeditor.discovery

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.context.annotation.PropertySource


@EnableEurekaServer
@SpringBootApplication
@PropertySource("bootstrap.yml")
class DiscoveryService

fun main(args: Array<String>) {
    SpringApplication(DiscoveryService::class.java).apply {
        run(*args)
    }
}