package com.se.wmeditor.gateway

import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.support.beans


fun beans() = beans {

    bean {
        DiscoveryClientRouteDefinitionLocator(ref())
    }

    bean<RouteLocator>("routing") {

        ref<RouteLocatorBuilder>().routes().route { r ->
            r.path("/api/**")
                    .filters { f -> f.addResponseHeader("X-TestHeader", "foobar") }
                .uri("lb://mnist-service")
        }.build()
    }
}