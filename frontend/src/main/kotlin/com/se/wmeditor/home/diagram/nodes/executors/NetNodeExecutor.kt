package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.ContextNetDescription
import com.se.wmeditor.common.Description
import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.home.diagram.nodes.NetNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinx.serialization.json.JSON

class NetNodeExecutor(private val nodeModel: NetNode) : AbstractNodeExecutor(nodeModel) {

    private lateinit var outNet: ValueHolderPort<NetDescription>

    override fun getPortById(portId: String): ValueHolderPort<out Description> {
        throw IllegalArgumentException("No port with id $portId")
    }

    override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
        outNet = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<NetDescription>>()
    }

    override suspend fun execute() {
        val sendingDescription = NetDescription(nodeModel.config.model, nodeModel.dataset)
        val contextDescription = ContextNetDescription(contextId, sendingDescription)
        val ans = JSON.parse<NetDescription>(post("/api/net/actions/init", JSON.stringify(contextDescription)))
        node.setSelected(false)
        node.outLinks().forEach { it.setSelected(false) }
        outNet.setValue(ans)
    }
}