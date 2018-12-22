package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.nodes.NetInitNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.serialization.json.JSON

class NetInitExecutor(private val nodeModel: NetInitNode) : AbstractNodeExecutor(nodeModel) {

  private lateinit var outNet: ValueHolderPort<TrainedNetMeta>

  override fun getPortById(portId: String): ValueHolderPort<out Any> {
    throw IllegalArgumentException("No port with id $portId")
  }

  override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
    outNet = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<TrainedNetMeta>>()
  }

  override suspend fun execute() {
    val initRequest = NetInitRequest(contextId, nodeModel.trainedNetMeta)
    val ans = JSON.parse(
      NetInitResponse.serializer(),
      post("/api/net/actions/init", JSON.stringify(NetInitRequest.serializer(), initRequest))
    )
    node.setSelected(false)
    node.outLinks().forEach { it.setSelected(false) }
    outNet.setValue(ans.trainedNetMeta)
  }
}