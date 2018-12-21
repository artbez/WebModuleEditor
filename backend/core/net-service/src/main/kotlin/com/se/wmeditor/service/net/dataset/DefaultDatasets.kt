package com.se.wmeditor.service.net.dataset

import com.se.wmeditor.common.*

val imageNet64 = DatasetDescription(
  label = "Imagenet",
  description = "Imagenet",
  type = DatasetType.IMAGENET,
  state = DatasetState(
    inputSize = listOf(3, 224, 224),
    outputSize = listOf(200)
  )
)

val tynyImageNet64 = DatasetDescription(
  label = "Imagenet",
  description = "Imagenet, 500 images",
  type = DatasetType.TINY_IMAGENET,
  state = DatasetState(
    inputSize = listOf(3, 64, 64),
    outputSize = listOf(200)
  )
)

val cifar10 = DatasetDescription(
  label = "Cifar 10",
  description = "Cifar 10",
  type = DatasetType.CIFAR10,
  state = DatasetState(
    inputSize = listOf(3, 32, 32),
    outputSize = listOf(10)
  )
)