package com.se.wmeditor.service.net.api.ws

import com.se.wmeditor.common.*
import com.se.wmeditor.service.net.core.NetActionService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.serialization.json.JSON
import org.deeplearning4j.nn.api.Model
import org.deeplearning4j.optimize.api.BaseTrainingListener
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

class TrainWSHandler(private val netActionService: NetActionService) : WebSocketHandler {

  private val logger = LoggerFactory.getLogger(TrainedNetMeta::class.java)

  override fun handle(session: WebSocketSession): Mono<Void> {
    return session
      .receive()
      .map { JSON.parse(NetTrainRequest.serializer(), it.payloadAsText) }
      .map { request ->
        val listener = TrainSessionListener(request.contextId, session)
        GlobalScope.future {
          val response = netActionService.trainNet(request.trainedNetMeta, request.datasetMeta, listOf(listener))
          JSON.stringify(NetTrainResponse.serializer(), NetTrainResponse(request.contextId, response))
        }.thenAccept {
          session.send(Mono.just(session.textMessage(it))).block()
        }
        Mono.`when`()
      }.flatMap { it }.toMono()
  }
}

private class TrainSessionListener(
  private val contextId: String,
  private val session: WebSocketSession
) : BaseTrainingListener() {

  private val logger = LoggerFactory.getLogger(TrainSessionListener::class.java)

  private var counter = 0

  override fun iterationDone(model: Model, iteration: Int, epoch: Int) {
    counter++
    val score = model.score()
    val logMsg = "Iteration $iteration done, score $score"
    logger.info(logMsg)
    val msg = JSON.stringify(NetProgressMsg.serializer(), NetProgressMsg(contextId, logMsg))
    session.send(Mono.just(session.textMessage(msg))).block()
  }
}