package com.se.wmeditor.common

import kotlinx.serialization.Serializable

@Serializable
data class ContextHolder(val contextId: String)

@Serializable
data class NetInitRequest(
  val contextId: String,
  val netMeta: NetMeta,
  val pretrained: DatasetType?
)

@Serializable
data class NetInitResponse(
  val contextId: String,
  val netMeta: NetMeta
)

@Serializable
data class NetTrainRequest(
  val contextId: String,
  val netMeta: NetMeta,
  val datasetMeta: DatasetMeta
)

@Serializable
data class NetTrainResponse(
  val contextId: String,
  val netMeta: NetMeta
)

@Serializable
data class NetEvalRequest(
  val contextId: String,
  val netMeta: NetMeta,
  val datasetMeta: DatasetMeta
)

@Serializable
data class NetEvalResponse(
  val contextId: String,
  val data: String
)
