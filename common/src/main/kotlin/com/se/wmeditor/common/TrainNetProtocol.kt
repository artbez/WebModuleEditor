package com.se.wmeditor.common

import kotlinx.serialization.Serializable

@Serializable
data class NetTrainRequest(
  val contextId: String,
  val trainedNetMeta: TrainedNetMeta,
  val datasetMeta: DatasetMeta
)

@Serializable
data class NetProgressMsg(
  val contextId: String,
  val msg: String,
  val type: String = "progress"
)

@Serializable
data class NetTrainResponse(
  val contextId: String,
  val trainedNetMeta: TrainedNetMeta,
  val type: String = "final"
)