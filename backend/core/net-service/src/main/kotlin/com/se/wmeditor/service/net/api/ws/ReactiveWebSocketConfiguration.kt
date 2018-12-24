package com.se.wmeditor.service.net.api.ws

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class ReactiveWebSocketConfiguration(val trainWsHandler: TrainWSHandler) {

  @Bean
  fun webSocketHandlerMapping(): HandlerMapping {
    val map = HashMap<String, WebSocketHandler?>()
    map["/api/ws/net/train"] = trainWsHandler

    val handlerMapping = SimpleUrlHandlerMapping()
    handlerMapping.order = 1
    handlerMapping.urlMap = map
    return handlerMapping
  }

  @Bean
  fun handlerAdapter(): WebSocketHandlerAdapter {
    return WebSocketHandlerAdapter()
  }
}
