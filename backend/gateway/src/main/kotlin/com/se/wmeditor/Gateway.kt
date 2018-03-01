package com.se.wmeditor

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan(basePackages = ["com.se.wmeditor"])
@EnableAutoConfiguration
@EnableScheduling
class Gateway

fun main(args: Array<String>) {
    runApplication<Gateway>(*args)
}