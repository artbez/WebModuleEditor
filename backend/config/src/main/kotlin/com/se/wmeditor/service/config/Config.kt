package com.se.wmeditor.service.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
//@EnableEurekaClient
@EnableConfigServer
@PropertySource("bootstrap.yaml")
class Config

fun main(args: Array<String>) {
    SpringApplication(Config::class.java).apply {
        run(*args)
    }
}

