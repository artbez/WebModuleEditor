package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.DatasetMeta
import com.se.wmeditor.common.DatasetState
import com.se.wmeditor.home.diagram.editor.nodes.panel.DiagramExecutionPanel
import com.se.wmeditor.home.diagram.nodes.DatasetNode
import com.se.wmeditor.home.diagram.nodes.ports.DatasetPortModel
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel

class DatasetExecutor(
  val datasetNode: DatasetNode,
  private val panel: DiagramExecutionPanel
) : AbstractNodeExecutor(datasetNode) {

  lateinit var outDataset: ValueHolderPort<DatasetMeta>

  override fun getPortById(portId: String): ValueHolderPort<DatasetState> {
    throw IllegalArgumentException("No port with id $portId")
  }

  override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
    if (port !is DatasetPortModel) {
      throw IllegalArgumentException("Port must have DatasetType type")
    }
    outDataset = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<DatasetMeta>>()
  }

  override suspend fun execute() {
    panel.startNode(node)
    node.setSelected(false)
    node.outLinks().forEach { it.setSelected(false) }
    outDataset.setValue(datasetNode.selectedDataset)
    panel.stopNode(node)
  }
}