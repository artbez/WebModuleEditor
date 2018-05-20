package com.se.wmeditor.common


enum class Dataset {
    NONE, IMAGENET, CIFAR10
}

enum class NetModel {
    VGG16
}

sealed class Description

class NetDescription(val model: NetModel, val pretrained: Dataset) : Description() {
    override fun toString(): String {
        return "[${model.name}, pretrained: ${pretrained.name}]"
    }
}

class DataDescription(val data: String) : Description() {
    override fun toString(): String {
        return "[$data]"
    }
}

class DatasetDescription(val dataset: Dataset) : Description() {
    override fun toString(): String {
        return "[dataset: ${dataset.name}]"
    }
}