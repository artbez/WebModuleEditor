package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.Description
import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.inPorts
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.serialization.ImplicitReflectionSerializer


abstract class AbstractNodeExecutor(val node: NodeModel) {

    private var dataPoints = node.inPorts().size
    lateinit var contextId: String
    protected abstract suspend fun execute()

    abstract fun getPortById(portId: String): ValueHolderPort<out Description>

    abstract fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor)

    suspend fun tryExecute() {
        if (dataPoints == 0) {
            node.setSelected(true)
            node.outLinks().forEach { it.setSelected(true) }
            execute()
        }
    }

    suspend fun dataReceived() {
        if (dataPoints == 0) {
            throw IllegalStateException("All data already received")
        }
        dataPoints--
        tryExecute()
    }
}

@UseExperimental(ImplicitReflectionSerializer::class)
fun createExecutor(node: NodeModel) = when (node) {
    is NetNode -> NetNodeExecutor(node)
    is NetTrainNode -> NetTrainExecutor(node)
    is NetEvalNode -> NetEvalExecutor(node)
    is DatasetNode -> DatasetExecutor(node)
    is AlertNode -> AlertExecutor(node)
    else -> throw IllegalStateException("Not supported node")
}