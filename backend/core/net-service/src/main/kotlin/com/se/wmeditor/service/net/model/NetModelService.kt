package com.se.wmeditor.service.net.model

import com.se.wmeditor.common.*


class NetModelService {

  private val allNets = listOf(vgg16)

  fun all() = allNets

  fun checkPretrained(netType: NetType, netState: NetState, datasetType: DatasetType): Boolean {
    val net = allNets.find { it.type == netType } ?: return false
    val pretrained = net.pretrained.find { it.type == datasetType } ?: return false
    return pretrained.state.inputSize == netState.inputSize && pretrained.state.outputSize == netState.outputSize
  }
}