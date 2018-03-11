package com.se.wmeditor.service.diagram

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@SpringBootApplication
@EnableEurekaClient
class DiagramService

fun main(args: Array<String>) {
    SpringApplication(DiagramService::class.java).apply {
        addInitializers(beans())
        run(*args)
    }
}


class EchoHandler {

    fun echo(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(request.bodyToMono(String::class.java), String::class.java)
    }
}