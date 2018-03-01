package com.se.wmeditor

import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse


fun beans() = beans {
    bean<EchoHandler>("echoHandler")

    bean<RouterFunction<ServerResponse>>("monoRouterFunction") {
        route(POST("/api/echo"), HandlerFunction { ref<EchoHandler>().echo(it) })
    }
}