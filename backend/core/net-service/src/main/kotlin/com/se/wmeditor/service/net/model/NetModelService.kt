package com.se.wmeditor.service.net.model

import com.se.wmeditor.common.*
import org.deeplearning4j.nn.graph.ComputationGraph


class NetModelService(private val netDaoService: NetDaoService) {

  private val allNets = listOf(vgg16)

  fun all() = allNets

  fun find(trainedNetMeta: TrainedNetMeta)
    = netDaoService.find(trainedNetMeta)

  fun save(trainedNetMeta: TrainedNetMeta, model: ComputationGraph) {
    netDaoService.save(trainedNetMeta, model)
  }

  fun checkPretrained(trainedNetMeta: TrainedNetMeta): Boolean {
    val pretrained = netDaoService.findAllSeq(trainedNetMeta.netMeta)
    return pretrained.any { it.datasets == trainedNetMeta.datasets }
  }

  fun allPretained(netMeta: NetMeta) =
    netDaoService.findAllSeq(netMeta).filter { it.datasets.isNotEmpty() }
}