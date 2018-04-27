package com.se.wmeditor.service.diagram

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO


@SpringBootApplication
//@EnableEurekaClient
@EnableScheduling
class DiagramService

fun main(args: Array<String>) {
    SpringApplication.run(DiagramService::class.java, *args)
}

class EchoHandler(val mnistModel: MnistModel) {

    fun echo(request: ServerRequest): Mono<ServerResponse> {
        val imageBytes = request.bodyToMono(ByteArray::class.java).block()
        val image = ImageIO.read(ByteArrayInputStream(imageBytes))
        return ServerResponse.ok().body(Mono.just(mnistModel.predict(image)), Int::class.java)
    }
}