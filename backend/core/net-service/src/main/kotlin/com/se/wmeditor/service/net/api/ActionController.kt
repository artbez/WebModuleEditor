package com.se.wmeditor.service.net.api

import com.se.wmeditor.common.*
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
class ActionController(private val netContextHolder: NetContextHolder) {

  @PostMapping("init")
  fun initNet(@RequestBody netInitRequest: NetInitRequest): NetInitResponse {
    val (contextId, netMeta, pretrained) = netInitRequest

    val context = netContextHolder.getNetContext(contextId)
    context.createNet(netMeta.type, netMeta.state, pretrained)

    return NetInitResponse(contextId, netMeta)
  }

  @PostMapping("train")
  fun trainNet(@RequestBody netTrainRequest: NetTrainRequest): NetTrainResponse {
    val (contextId, netMeta, datasetMeta) = netTrainRequest

    val context = netContextHolder.getNetContext(contextId)
    context.trainNet(netMeta.state, datasetMeta.type, datasetMeta.state)

    return NetTrainResponse(contextId, netMeta)
  }

  @PostMapping("eval")
  fun evalNet(@RequestBody netEvalRequest: NetEvalRequest): NetEvalResponse {
    val (contextId, netMeta, datasetMeta) = netEvalRequest

    val context = netContextHolder.getNetContext(contextId)
    val evaluation = context.evaluateNet(netMeta.state, datasetMeta.type, datasetMeta.state)

    return NetEvalResponse(contextId, evaluation)
  }
}