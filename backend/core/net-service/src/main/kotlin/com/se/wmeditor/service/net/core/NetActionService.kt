package com.se.wmeditor.service.net.core

import com.se.wmeditor.common.*
import com.se.wmeditor.service.net.model.NetModelService
import org.deeplearning4j.datasets.fetchers.DataSetType
import org.deeplearning4j.datasets.iterator.impl.CifarDataSetIterator
import org.deeplearning4j.datasets.iterator.impl.TinyImageNetDataSetIterator
import org.deeplearning4j.optimize.api.BaseTrainingListener
import org.deeplearning4j.zoo.model.VGG16
import org.nd4j.evaluation.classification.Evaluation
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono


class NetActionService(private val netModelService: NetModelService) {

  private val logger = LoggerFactory.getLogger(NetActionService::class.java)
  private val batchSize = 1

  fun createNet(trainedNetMeta: TrainedNetMeta) {
    val netType = trainedNetMeta.netMeta.type
    val netState = trainedNetMeta.netMeta.state

    val zooModel = when (netType) {
      NetType.VGG16 -> VGG16
        .builder()
        .numClasses(netState.outputSize[0])
        .inputShape(netState.inputSize.toIntArray())
        .build()

      else -> TODO()
    }

    val pretrained = netModelService.checkPretrained(trainedNetMeta)
    if (trainedNetMeta.datasets.isNotEmpty() && !pretrained) {
      throw IllegalArgumentException("HO-HO-HO-1")
    }

    val model = zooModel.init()
    netModelService.save(trainedNetMeta, model)
  }

  fun trainNet(
    trainedNetMeta: TrainedNetMeta,
    datasetMeta: DatasetMeta,
    listeners: List<BaseTrainingListener> = emptyList()
  ): TrainedNetMeta {

    val newMeta = trainedNetMeta.copy(datasets = trainedNetMeta.datasets.plus(datasetMeta))
    netModelService.find(newMeta)?.let { return newMeta }

    val oldNet = netModelService.find(trainedNetMeta) ?: throw IllegalArgumentException("HO-HO-HO-2")
    val netState = trainedNetMeta.netMeta.state

    val datasetIterator = when (datasetMeta.type) {
      DatasetType.TINY_IMAGENET ->
        TinyImageNetDataSetIterator(batchSize, netState.inputSize.subList(1, 3).toIntArray(), DataSetType.TRAIN)

      DatasetType.CIFAR10 -> CifarDataSetIterator(batchSize, 30, netState.inputSize.reversed().toIntArray())

      else -> throw IllegalArgumentException("No such datasetType")
    }

//    var iternum = 0
//    while (datasetIterator.hasNext()) {
//      iternum++
//      datasetIterator.next()
//    }
//    datasetIterator.reset()

    listeners.forEach {
      oldNet.addListeners(it)
    }


    oldNet.fit(datasetIterator)
    netModelService.save(newMeta, oldNet)

    return newMeta
  }

  fun evaluateNet(trainedNetMeta: TrainedNetMeta, datasetMeta: DatasetMeta): String {
    val netModel = netModelService.find(trainedNetMeta) ?: throw IllegalArgumentException("HO-HO-HO-3")

    val netState = trainedNetMeta.netMeta.state
    val datasetIterator = when (datasetMeta.type) {
      DatasetType.TINY_IMAGENET ->
        TinyImageNetDataSetIterator(batchSize, netState.inputSize.subList(1, 3).toIntArray(), DataSetType.TRAIN)

      DatasetType.CIFAR10 -> CifarDataSetIterator(batchSize, 10, netState.inputSize.reversed().toIntArray())

      else -> throw IllegalArgumentException("No such datasetType")
    }

    val evaluation = Evaluation(netState.outputSize[0])
    var count = 10
    while (datasetIterator.hasNext() && count > 0) {
      val next = datasetIterator.next()
      val output = netModel.output(next.features)
      evaluation.eval(next.labels, output[0])
      logger.info(evaluation.toString())
      count--
    }

    val evaluate = netModel.evaluate<Evaluation>(datasetIterator)
    return evaluate.toString()
  }
}

data class TrainNetServiceResponse(
  val iterationLength: Int,
  val netMetaMono: Mono<TrainedNetMeta>
)