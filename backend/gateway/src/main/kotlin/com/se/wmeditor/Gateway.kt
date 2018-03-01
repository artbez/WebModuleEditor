package com.se.wmeditor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@SpringBootApplication
@ComponentScan(basePackages = ["com.se.wmeditor"])
class Gateway

fun main(args: Array<String>) {
    SpringApplication(Gateway::class.java).apply {
        addInitializers(beans())
        run(*args)
    }
}


class EchoHandler {

    fun echo(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(request.bodyToMono(String::class.java), String::class.java)
    }
}