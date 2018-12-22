package com.se.wmeditor.service.net.model

import com.se.wmeditor.common.*
import com.se.wmeditor.service.net.dataset.cifar10
import com.se.wmeditor.service.net.dataset.imageNet64

val vgg16 = NetDescription(
  description = "VGG 16 for image classification",
  label = "VGG 16",
  type = NetType.VGG16,
  state = NetState(
    inputSize = listOf(3, 244, 244),
    outputSize = listOf(200)
  )
)
