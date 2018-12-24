package com.se.wmeditor.common

import kotlinx.serialization.Serializable

@Serializable
data class ContextHolder(val contextId: String)

@Serializable
data class NetInitRequest(
  val contextId: String,
  val trainedNetMeta: TrainedNetMeta
)

@Serializable
data class NetInitResponse(
  val contextId: String,
  val trainedNetMeta: TrainedNetMeta
)

@Serializable
data class NetEvalRequest(
  val contextId: String,
  val trainedNetMeta: TrainedNetMeta,
  val datasetMeta: DatasetMeta
)

@Serializable
data class NetEvalResponse(
  val contextId: String,
  val data: String
)
