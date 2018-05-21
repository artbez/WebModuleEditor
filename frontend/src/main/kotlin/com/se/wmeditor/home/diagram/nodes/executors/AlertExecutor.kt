package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.DataDescription
import com.se.wmeditor.common.Description
import com.se.wmeditor.home.diagram.nodes.AlertNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlin.browser.window

class AlertExecutor(alertNode: AlertNode) : AbstractNodeExecutor(alertNode) {
    override fun getPortById(portId: String): ValueHolderPort<out Description> = inData

    override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
        throw IllegalStateException("Alert has no output ports")
    }

    private var inData: ValueHolderPort<DataDescription> = ValueHolderPort(alertNode.dataInputPort.getID(), this)

    override suspend fun execute() {
        node.setSelected(false)
        node.outLinks().forEach { it.setSelected(false) }
        window.alert(inData.getValue()!!.data)
    }
}