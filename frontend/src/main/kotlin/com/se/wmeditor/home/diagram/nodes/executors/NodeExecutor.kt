package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.Description
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.diagram.nodes.ports.createValueHolder
import com.se.wmeditor.home.inPorts
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel


abstract class AbstractNodeExecutor(protected val node: NodeModel) {

    var dataPoints = node.inPorts().size

    protected abstract fun execute()

    fun tryExecute() {
        if (dataPoints == 0) {
            node.setSelected(true)
            node.outLinks().forEach { it.setSelected(true) }
            execute()
            node.setSelected(false)
            node.outLinks().forEach { it.setSelected(false) }
        }
    }

    fun dataReceived() {
        if (dataPoints == 0) {
            throw IllegalStateException("All data already received")
        }
        dataPoints--
        tryExecute()
    }
}

class DefaultNodeExecutor(node: NodeModel) : AbstractNodeExecutor(node) {

    var inPorts = node.inPorts().map { it.getID() to createValueHolder(it, this) }
    var linkedPorts: MutableList<ValueHolderPort<out Description>> = mutableListOf()

    override fun execute() {
        console.log("Executing ${node.getID()} ${node.getType()}")
        inPorts.forEachIndexed { index, valueHolderPortEntry ->
            console.log("Value in port $index = ${valueHolderPortEntry.second.value}")
        }
        linkedPorts.forEach {
            it.value = null
        }
    }
}