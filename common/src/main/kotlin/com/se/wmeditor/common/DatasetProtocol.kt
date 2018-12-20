package com.se.wmeditor.common

import kotlinx.serialization.Serializable

enum class DatasetType {
  IMAGENET, TINY_IMAGENET, CIFAR10, LWF
}

@Serializable
data class DatasetDescription(
  val type: DatasetType,
  val label: String,
  val description: String,
  var state: DatasetState
) {
  fun meta() = DatasetMeta(type, state)
}

@Serializable
data class DatasetState(
  val inputSize: List<Int>,
  val outputSize: List<Int>
)

@Serializable
data class DatasetMeta(
  val type: DatasetType,
  val state: DatasetState
)
