package com.se.wmeditor.service.net

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient


@SpringBootApplication
@EnableEurekaClient
class NetService

fun main(args: Array<String>) {
    SpringApplication.run(NetService::class.java, *args)
}