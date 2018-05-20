package com.se.wmeditor.home.diagram.nodes.ports

import com.se.wmeditor.home.diagram.nodes.Dataset
import com.se.wmeditor.home.diagram.nodes.NetModel
import com.se.wmeditor.home.diagram.nodes.executors.AbstractNodeExecutor

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


class ValueHolderPort<T : Description>(private val nodeExecutor: AbstractNodeExecutor) {
    var value: T? = null
        set(value) {
            field = value
            nodeExecutor.dataReceived()
        }
}

fun createValueHolder(port: InitialPortModel, nodeExecutor: AbstractNodeExecutor) = when (port) {
    is NetPortModel -> ValueHolderPort<NetDescription>(nodeExecutor)
    is DataPortModel -> ValueHolderPort<DataDescription>(nodeExecutor)
    is DatasetPortModel -> ValueHolderPort<DatasetDescription>(nodeExecutor)
}