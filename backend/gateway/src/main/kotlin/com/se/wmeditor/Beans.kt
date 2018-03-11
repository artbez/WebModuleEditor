package com.se.wmeditor

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.support.beans


fun beans() = beans {

    bean<RouteLocator>("routing") {

        ref<RouteLocatorBuilder>().routes().route { r ->
            r.path("/api/**")
                    .filters { f -> f.addResponseHeader("X-TestHeader", "foobar") }
                    .uri("http://localhost:8080")
        }.build()
    }
}