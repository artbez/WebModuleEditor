package com.se.wmeditor.service.net.api

import com.se.wmeditor.common.*
import com.se.wmeditor.service.net.core.NetActionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/net/actions")
class ActionController(private val netActionService: NetActionService) {

  @PostMapping("init")
  fun initNet(@RequestBody netInitRequest: NetInitRequest): NetInitResponse {
    netActionService.createNet(netInitRequest.trainedNetMeta)
    return NetInitResponse(netInitRequest.contextId, netInitRequest.trainedNetMeta)
  }

  @Deprecated("use ws version")
  @PostMapping("train")
  fun trainNet(@RequestBody netTrainRequest: NetTrainRequest): NetTrainResponse {
    val (contextId, trainedNetMeta, datasetMeta) = netTrainRequest
    val trained = netActionService.trainNet(trainedNetMeta, datasetMeta)
    return NetTrainResponse(contextId, TODO())
  }

  @PostMapping("eval")
  fun evalNet(@RequestBody netEvalRequest: NetEvalRequest): NetEvalResponse {
    val (contextId, trainedNetMeta, datasetMeta) = netEvalRequest
    val evaluation = netActionService.evaluateNet(trainedNetMeta, datasetMeta)
    return NetEvalResponse(contextId, evaluation)
  }
}