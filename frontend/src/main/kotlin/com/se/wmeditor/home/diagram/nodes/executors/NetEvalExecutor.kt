package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.editor.nodes.panel.DiagramExecutionPanel
import com.se.wmeditor.home.diagram.nodes.NetEvalNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.serialization.json.JSON

class NetEvalExecutor(
  private val netEvalNode: NetEvalNode,
  private val panel: DiagramExecutionPanel
) : AbstractNodeExecutor(netEvalNode) {

  private val inNet: ValueHolderPort<TrainedNetMeta> = ValueHolderPort(netEvalNode.inputNetPort.getID(), this)
  private val inDataset: ValueHolderPort<DatasetMeta> = ValueHolderPort(netEvalNode.inputDatasetPort.getID(), this)
  private lateinit var outData: ValueHolderPort<String>

  override fun getPortById(portId: String): ValueHolderPort<out Any> = when (portId) {
    inNet.portId -> inNet
    inDataset.portId -> inDataset
    else -> throw IllegalArgumentException("No port with id $portId")
  }

  override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
    outData = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<String>>()
  }

  override suspend fun execute() {
    panel.startNode(netEvalNode)
    val datasetMeta = inDataset.getValue()!!
    val trainedNetMeta = inNet.getValue()!!
    val evalRequest = NetEvalRequest(contextId, trainedNetMeta, datasetMeta)
    val ans = JSON.parse(
      NetEvalResponse.serializer(),
      post("/api/net/actions/eval", JSON.stringify(NetEvalRequest.serializer(), evalRequest))
    )
    node.setSelected(false)
    node.outLinks().forEach { it.setSelected(false) }
    outData.setValue(ans.data)
    panel.stopNode(node)
  }
}