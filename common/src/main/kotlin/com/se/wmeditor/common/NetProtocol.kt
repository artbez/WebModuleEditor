package com.se.wmeditor.common

import kotlinx.serialization.Serializable

enum class NetType {
  AlexNet, SimpleCNN, VGG16
}

@Serializable
data class NetDescription(
  val type: NetType,
  val label: String,
  val description: String,
  var state: NetState
)

@Serializable
data class NetState(
  val inputSize: List<Int>,
  val outputSize: List<Int>
)

@Serializable
data class NetMeta(
  val type: NetType,
  val state: NetState
)

@Serializable
data class TrainedNetMeta(
  val netMeta: NetMeta,
  val datasets: List<DatasetMeta>
)

@Serializable
data class PretrainedInfo(
  val nets: List<TrainedNetMeta>
)