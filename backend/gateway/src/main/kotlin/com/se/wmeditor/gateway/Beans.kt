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

      ref<RouteLocatorBuilder>().routes()
        .route { r -> r.path("/api/net/**").uri("lb://net-service") }
        .route { r -> r.path("/api/ws/net/**").uri("lb:ws://net-service") }.build()
    }
}