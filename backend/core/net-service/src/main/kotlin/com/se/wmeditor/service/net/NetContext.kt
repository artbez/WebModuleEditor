package com.se.wmeditor.service.net

import com.se.wmeditor.common.Dataset
import com.se.wmeditor.common.DatasetDescription
import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.common.NetModel
import org.deeplearning4j.datasets.fetchers.DataSetType
import org.deeplearning4j.datasets.iterator.impl.CifarDataSetIterator
import org.deeplearning4j.datasets.iterator.impl.TinyImageNetDataSetIterator
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.deeplearning4j.zoo.PretrainedType
import org.deeplearning4j.zoo.model.VGG16
import org.nd4j.evaluation.classification.Evaluation

class NetContext {

  lateinit var netModel: ComputationGraph
  private val batchSize = 64

  fun createNet(netDescription: NetDescription) {
    if (netDescription.model == NetModel.VGG16) {
      val zooModel = VGG16.builder().build()
      netModel = when (netDescription.pretrained) {
        Dataset.IMAGENET -> zooModel.initPretrained<ComputationGraph>(PretrainedType.IMAGENET)
        Dataset.CIFAR10 -> zooModel.initPretrained<ComputationGraph>(PretrainedType.CIFAR10)
        Dataset.NONE -> zooModel.init()
      } as ComputationGraph
      netModel.setListeners(ScoreIterationListener(10))
    }
  }

  fun trainNet(datasetDescription: DatasetDescription) {
    if (datasetDescription.dataset == Dataset.NONE) {
      return
    }
    val datasetIterator = when (datasetDescription.dataset) {
      Dataset.IMAGENET -> TinyImageNetDataSetIterator(batchSize, DataSetType.TRAIN)
      Dataset.CIFAR10 -> CifarDataSetIterator(batchSize, 10, true)
      else -> throw IllegalArgumentException("No such dataset")
    }

    netModel.fit(datasetIterator)
  }

  fun evaluateNet(datasetDescription: DatasetDescription): Double {
    if (datasetDescription.dataset == Dataset.NONE) {
      throw IllegalArgumentException("Cannot evaluate on none dataset")
    }
    val datasetIterator = when (datasetDescription.dataset) {
      Dataset.IMAGENET -> TinyImageNetDataSetIterator(batchSize, DataSetType.TEST)
      Dataset.CIFAR10 -> CifarDataSetIterator(batchSize, 10, false)
      else -> throw IllegalArgumentException("No such dataset")
    }

    return netModel.evaluate<Evaluation>(datasetIterator).accuracy()
  }
}