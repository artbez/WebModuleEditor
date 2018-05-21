package com.se.wmeditor.home.diagram.nodes.ports

import com.se.wmeditor.common.DataDescription
import com.se.wmeditor.common.DatasetDescription
import com.se.wmeditor.common.Description
import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.home.diagram.nodes.executors.AbstractNodeExecutor

class ValueHolderPort<T : Description>(val portId: String, private val nodeExecutor: AbstractNodeExecutor) {
    private var value: T? = null

    fun getValue(): T? = value

    suspend fun setValue(newValue: T?) {
        value = newValue
        nodeExecutor.dataReceived()
    }
}

fun createValueHolder(port: InitialPortModel, nodeExecutor: AbstractNodeExecutor) = when (port) {
    is NetPortModel -> ValueHolderPort<NetDescription>(port.getID(), nodeExecutor)
    is DataPortModel -> ValueHolderPort<DataDescription>(port.getID(), nodeExecutor)
    is DatasetPortModel -> ValueHolderPort<DatasetDescription>(port.getID(), nodeExecutor)
}