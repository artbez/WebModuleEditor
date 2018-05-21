package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.nodes.NetEvalNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.serialization.json.JSON

class NetEvalExecutor(private val netEvalNode: NetEvalNode) : AbstractNodeExecutor(netEvalNode) {

    override fun getPortById(portId: String): ValueHolderPort<out Description> = when (portId) {
        inNet.portId -> inNet
        inDataset.portId -> inDataset
        else -> throw IllegalArgumentException("No port with id $portId")
    }


    override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
        outData = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<DataDescription>>()
    }

    private val inNet: ValueHolderPort<NetDescription> = ValueHolderPort(netEvalNode.inputNetPort.getID(), this)
    private val inDataset: ValueHolderPort<DatasetDescription> = ValueHolderPort(netEvalNode.inputDatasetPort.getID(), this)

    lateinit var outData: ValueHolderPort<DataDescription>

    override suspend fun execute() {
        val sendingDescription = inDataset.getValue()!!
        val contextDescription = ContextDatasetDesciption(contextId, sendingDescription)
        val ans = JSON.parse<DataDescription>(post("/api/net/actions/eval", JSON.stringify(contextDescription)))
        node.setSelected(false)
        node.outLinks().forEach { it.setSelected(false) }
        outData.setValue(ans)
    }
}