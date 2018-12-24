package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.editor.nodes.panel.DiagramExecutionPanel
import com.se.wmeditor.home.diagram.nodes.NetTrainNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.w3c.dom.WebSocket

class NetTrainExecutor(
  nodeModel: NetTrainNode,
  private val panel: DiagramExecutionPanel
) : AbstractNodeExecutor(nodeModel) {

  private val inNet: ValueHolderPort<TrainedNetMeta> = ValueHolderPort(nodeModel.inputNetPort.getID(), this)
  private val inDataset: ValueHolderPort<DatasetMeta> = ValueHolderPort(nodeModel.inputDatasetPort.getID(), this)

  lateinit var outNet: ValueHolderPort<TrainedNetMeta>

  override fun getPortById(portId: String): ValueHolderPort<out Any> = when (portId) {
    inNet.portId -> inNet
    inDataset.portId -> inDataset
    else -> throw IllegalArgumentException("No port with id $portId")
  }

  override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
    outNet = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<TrainedNetMeta>>()
  }

  override suspend fun execute() {
    panel.startNode(node)
    val datasetMeta = inDataset.getValue()!!
    val trainedNetMeta = inNet.getValue()!!
    val netTrainRequest = NetTrainRequest(contextId, trainedNetMeta, datasetMeta)
    val clientWebSocket = WebSocket("ws://localhost:9000/api/ws/net/train")
    clientWebSocket.onopen = {
      clientWebSocket.send(JSON.stringify(NetTrainRequest.serializer(), netTrainRequest))
    }
    clientWebSocket.onerror = {
      clientWebSocket.close()
      panel.stopNode(node)
    }
    clientWebSocket.onmessage = {
      val data = it.asDynamic().data
      val type = JSON.nonstrict.parse(ResponseTypeWrapper.serializer(), data).type
      when (type) {

        "progress" -> {
          val ans = JSON.parse(NetProgressMsg.serializer(), data)
          panel.update(node, ans.msg)
        }

        "final" -> GlobalScope.launch {
          val ans = JSON.parse(NetTrainResponse.serializer(), data)
          node.setSelected(false)
          node.outLinks().forEach { it.setSelected(false) }
          outNet.setValue(ans.trainedNetMeta)
          clientWebSocket.close()
          panel.stopNode(node)
        }

        else -> TODO()
      }
    }
  }
}

@Serializable
class ResponseTypeWrapper(val type: String)