package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.home.diagram.editor.nodes.panel.DiagramExecutionPanel
import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.inPorts
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel

abstract class AbstractNodeExecutor(val node: NodeModel) {

  private var dataPoints = node.inPorts().size
  lateinit var contextId: String
  protected abstract suspend fun execute()

  abstract fun getPortById(portId: String): ValueHolderPort<out Any>

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

fun createExecutor(node: NodeModel, panel: DiagramExecutionPanel) = when (node) {
  is NetInitNode -> NetInitExecutor(node, panel)
  is NetTrainNode -> NetTrainExecutor(node, panel)
  is NetEvalNode -> NetEvalExecutor(node, panel)
  is DatasetNode -> DatasetExecutor(node, panel)
  is AlertNode -> AlertExecutor(node, panel)
  else -> throw IllegalStateException("Not supported node")
}