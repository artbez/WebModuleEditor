package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.nodes.NetTrainNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON

class NetTrainExecutor(nodeModel: NetTrainNode) : AbstractNodeExecutor(nodeModel) {

  private val inNet: ValueHolderPort<NetMeta> = ValueHolderPort(nodeModel.inputNetPort.getID(), this)
  private val inDataset: ValueHolderPort<DatasetMeta> = ValueHolderPort(nodeModel.inputDatasetPort.getID(), this)

  lateinit var outNet: ValueHolderPort<NetMeta>

  override fun getPortById(portId: String): ValueHolderPort<out Any> = when (portId) {
    inNet.portId -> inNet
    inDataset.portId -> inDataset
    else -> throw IllegalArgumentException("No port with id $portId")
  }

  override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
    outNet = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<NetMeta>>()
  }

  override suspend fun execute() {
    val datasetMeta = inDataset.getValue()!!
    val netMeta = inNet.getValue()!!
    val netTrainRequest = NetTrainRequest(contextId, netMeta, datasetMeta)
    val ans = JSON.parse(
      NetTrainResponse.serializer(),
      post("/api/net/actions/train", JSON.stringify(NetTrainRequest.serializer(), netTrainRequest))
    )
    node.setSelected(false)
    node.outLinks().forEach { it.setSelected(false) }
    outNet.setValue(ans.netMeta)
  }
}