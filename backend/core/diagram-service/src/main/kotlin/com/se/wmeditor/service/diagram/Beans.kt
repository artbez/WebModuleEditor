package com.se.wmeditor.service.diagram

import org.springframework.web.reactive.function.server.*

fun beans() = org.springframework.context.support.beans {
    bean<EchoHandler>("echoHandler")

    bean<RouterFunction<ServerResponse>>("monoRouterFunction") {
        RouterFunctions.route(RequestPredicates.POST("/api/echo"), HandlerFunction { ref<EchoHandler>().echo(it) })
    }
}