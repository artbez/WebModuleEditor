package com.se.wmeditor.service.mnist

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.web.reactive.function.server.*


class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(ctx: GenericApplicationContext) = org.springframework.context.support.beans {
        bean("echoHandler") { EchoHandler(ref("mnistModel")) }

        bean<MnistModel>("mnistModel")

        bean<RouterFunction<ServerResponse>>("monoRouterFunction") {
            RouterFunctions.route(RequestPredicates.POST("/api/mnist"), HandlerFunction { ref<EchoHandler>().echo(it) })
        }
    }.initialize(ctx)
}