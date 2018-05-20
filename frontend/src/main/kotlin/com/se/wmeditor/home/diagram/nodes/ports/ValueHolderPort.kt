package com.se.wmeditor.home.diagram.nodes.ports

import com.se.wmeditor.common.DataDescription
import com.se.wmeditor.common.DatasetDescription
import com.se.wmeditor.common.Description
import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.home.diagram.nodes.executors.AbstractNodeExecutor

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