package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.home.diagram.nodes.ports.createValueHolder
import com.se.wmeditor.home.inPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel

class ComputationGraph(val nodes: List<NodeModel>) {

    private val executors: List<AbstractNodeExecutor>

    init {
        nodes.forEach { it.setSelected(false) }
        val executorMap = nodes.map { it.getID() to DefaultNodeExecutor(it) }.toMap()
        nodes.forEach { sourceNode ->
            sourceNode.outLinks().forEach {
                val targetPort = it.inPort()
                val targetExecutor = executorMap[targetPort.getNode().getID()] ?: throw IllegalStateException("Node must have executor")
                executorMap[sourceNode.getID()]!!.linkedPorts.add(createValueHolder(targetPort, targetExecutor))
            }
        }
        executors = executorMap.values.toList()
    }

    fun execute() {
        executors.forEach { it.tryExecute() }
    }
}