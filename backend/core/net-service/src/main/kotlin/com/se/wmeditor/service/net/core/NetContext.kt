package com.se.wmeditor.service.net.core

import com.se.wmeditor.common.*
import com.se.wmeditor.service.net.model.NetModelService
import org.deeplearning4j.datasets.fetchers.DataSetType
import org.deeplearning4j.datasets.iterator.impl.CifarDataSetIterator
import org.deeplearning4j.datasets.iterator.impl.TinyImageNetDataSetIterator
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.deeplearning4j.zoo.PretrainedType
import org.deeplearning4j.zoo.model.VGG16
import org.nd4j.evaluation.classification.Evaluation
import org.deeplearning4j.api.storage.StatsStorage
import org.nd4j.linalg.dataset.DataSet
import org.slf4j.LoggerFactory


class NetContext(private val netModelService: NetModelService) {

  private val logger = LoggerFactory.getLogger(NetContext::class.java)

  @Volatile
  lateinit var netModel: ComputationGraph

  private val batchSize = 64

  fun createNet(netType: NetType, netState: NetState, datasetType: DatasetType?) {
    val zooModel = when (netType) {
      NetType.VGG16 -> VGG16
        .builder()
        .numClasses(netState.outputSize[0])
        .inputShape(netState.inputSize.toIntArray())
        .build()

      else -> TODO()
    }

    netModel = when {
      datasetType == null -> zooModel.init()

      !netModelService.checkPretrained(netType, netState, datasetType) ->
        throw IllegalArgumentException("Incorrect pretrained size")

      datasetType == DatasetType.IMAGENET -> {
        zooModel.initPretrained<ComputationGraph>(PretrainedType.IMAGENET)
      }

      datasetType == DatasetType.CIFAR10 ->
        zooModel.initPretrained<ComputationGraph>(PretrainedType.CIFAR10)

      else -> TODO()

    } as ComputationGraph

    netModel.setListeners(ScoreIterationListener(10))
  }

  fun trainNet(netState: NetState, datasetType: DatasetType, datasetState: DatasetState) {

    if (netState.inputSize != datasetState.inputSize || netState.outputSize != datasetState.outputSize) {
      throw java.lang.IllegalArgumentException("Incorrect dataset state")
    }

    val datasetIterator = when (datasetType) {
      DatasetType.TINY_IMAGENET ->
        TinyImageNetDataSetIterator(batchSize, datasetState.inputSize.subList(1, 3).toIntArray(), DataSetType.TRAIN)

      DatasetType.CIFAR10 -> CifarDataSetIterator(batchSize, datasetState.inputSize.reversed().toIntArray())

      else -> throw IllegalArgumentException("No such datasetType")
    }

    netModel.fit(datasetIterator)
  }

  fun evaluateNet(netState: NetState, datasetType: DatasetType, datasetState: DatasetState): String {

    if (netState.inputSize != datasetState.inputSize || netState.outputSize != datasetState.outputSize) {
      throw java.lang.IllegalArgumentException("Incorrect dataset state")
    }

    val datasetIterator = when (datasetType) {
      DatasetType.TINY_IMAGENET ->
        TinyImageNetDataSetIterator(batchSize, datasetState.inputSize.subList(1, 3).toIntArray(), DataSetType.TRAIN)

      DatasetType.CIFAR10 -> CifarDataSetIterator(batchSize, 10, datasetState.inputSize.reversed().toIntArray())

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
    //netModel.setListeners(StatsListener(),new ScoreIterationListener(freIterations));

    val evaluate = netModel.evaluate<Evaluation>(datasetIterator)
    return evaluate.toString()
  }
}