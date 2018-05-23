package com.se.wmeditor.home

import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.home.diagram.nodes.ports.InitialPortModel
import com.se.wmeditor.home.diagram.nodes.ports.PortType
import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.DiagramModel
import com.se.wmeditor.wrappers.react.diagrams.models.LinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel

fun DiagramEngine.setup(): DiagramEngine {

    installDefaultFactories()

    registerNodeFactory(NetNodeFactory.instance)
    registerNodeFactory(NetTrainNodeFactory.instance)
    registerNodeFactory(NetEvalNodeFactory.instance)
    registerNodeFactory(UploadDatasetNodeFactory.instance)
    registerNodeFactory(AlertNodeFactory.instance)
    registerNodeFactory(DatasetNodeFactory.instance)

    val model = DiagramModel()
    setDiagramModel(model)

    return this
}

fun NodeModel.neighbors() = getPorts().toMap().values.map { it as InitialPortModel }
    .flatMap { it.getLinks().toMap().values.flatMap { listOf(it.getSourcePort(), it.getTargetPort()) } }
    .map { it as InitialPortModel }
    .map { it.getNode() }
    .filter { it.getID() != this.getID() }

fun NodeModel.inPorts() = getPorts().toMap().values.map { it as InitialPortModel }.filter { it.type == PortType.In }

fun NodeModel.outPorts() = getPorts().toMap().values.map { it as InitialPortModel }.filter { it.type == PortType.Out }

fun NodeModel.outLinks() = outPorts().flatMap { it.getLinks().toMap().values }

fun LinkModel<*>.inPort(): InitialPortModel {
    val initialSource = getSourcePort() as InitialPortModel
    val initialTarget = getTargetPort() as InitialPortModel

    return if (initialSource.type == PortType.In) initialSource else initialTarget
}

fun LinkModel<*>.outPort(): InitialPortModel {
    val initialSource = getSourcePort() as InitialPortModel
    val initialTarget = getTargetPort() as InitialPortModel

    return if (initialSource.type == PortType.Out) initialSource else initialTarget
}