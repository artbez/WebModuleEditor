package com.se.wmeditor.service.net.api

import com.se.wmeditor.common.*
import com.se.wmeditor.service.net.core.NetActionService
import com.se.wmeditor.service.net.core.NetContextHolder
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JSON
import kotlinx.serialization.stringify
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/net/actions")
class ActionController(private val netActionService: NetActionService) {

  @PostMapping("init")
  fun initNet(@RequestBody netInitRequest: NetInitRequest): NetInitResponse {
    netActionService.createNet(netInitRequest.trainedNetMeta)
    return NetInitResponse(netInitRequest.contextId, netInitRequest.trainedNetMeta)
  }

  @PostMapping("train")
  fun trainNet(@RequestBody netTrainRequest: NetTrainRequest): NetTrainResponse {
    val (contextId, trainedNetMeta, datasetMeta) = netTrainRequest
    val trained = netActionService.trainNet(trainedNetMeta, datasetMeta)
    return NetTrainResponse(contextId, trained)
  }

  @PostMapping("eval")
  fun evalNet(@RequestBody netEvalRequest: NetEvalRequest): NetEvalResponse {
    val (contextId, trainedNetMeta, datasetMeta) = netEvalRequest
    val evaluation = netActionService.evaluateNet(trainedNetMeta, datasetMeta)
    return NetEvalResponse(contextId, evaluation)
  }
}