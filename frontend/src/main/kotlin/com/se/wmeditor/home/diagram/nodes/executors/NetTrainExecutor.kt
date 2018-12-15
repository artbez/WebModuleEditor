package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.DatasetDescription
import com.se.wmeditor.common.Description
import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.home.diagram.nodes.NetTrainNode
import com.se.wmeditor.home.diagram.nodes.ports.ValueHolderPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel

class NetTrainExecutor(nodeModel: NetTrainNode) : AbstractNodeExecutor(nodeModel) {

    private val inNet: ValueHolderPort<NetDescription> = ValueHolderPort(nodeModel.inputNetPort.getID(), this)
    private val inDataset: ValueHolderPort<DatasetDescription> = ValueHolderPort(nodeModel.inputDatasetPort.getID(), this)

    lateinit var outNet: ValueHolderPort<NetDescription>

    override fun getPortById(portId: String): ValueHolderPort<out Description> = when (portId) {
        inNet.portId -> inNet
        inDataset.portId -> inDataset
        else -> throw IllegalArgumentException("No port with id $portId")
    }

    override fun attachPort(port: PortModel, targetExecutor: AbstractNodeExecutor) {
        outNet = targetExecutor.getPortById(port.getID()).unsafeCast<ValueHolderPort<NetDescription>>()
    }

    override suspend fun execute() {
        node.setSelected(false)
        node.outLinks().forEach { it.setSelected(false) }
        outNet.setValue(inNet.getValue())
    }
}