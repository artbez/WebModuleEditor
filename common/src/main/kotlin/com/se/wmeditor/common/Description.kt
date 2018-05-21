package com.se.wmeditor.common

import kotlinx.serialization.Serializable


enum class Dataset {
    NONE, IMAGENET, CIFAR10
}

enum class NetModel {
    VGG16
}

sealed class Description

@Serializable
class NetDescription(val model: NetModel, val pretrained: Dataset) : Description() {
    override fun toString(): String {
        return "[${model.name}, pretrained: ${pretrained.name}]"
    }
}

@Serializable
class DataDescription(val data: String) : Description() {
    override fun toString(): String {
        return "[$data]"
    }
}

@Serializable
class DatasetDescription(val dataset: Dataset) : Description() {
    override fun toString(): String {
        return "[dataset: ${dataset.name}]"
    }
}