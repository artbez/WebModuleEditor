package com.se.wmeditor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan(basePackages = ["com.se.wmeditor"])
class Gateway

fun main(args: Array<String>) {
    SpringApplication(Gateway::class.java).apply {
        addInitializers(beans())
        run(*args)
    }
}