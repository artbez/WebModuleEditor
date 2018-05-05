package com.se.wmeditor.wrappers.react.diagrams

import com.se.wmeditor.home.diagram.node.NetNode
import com.se.wmeditor.home.diagram.node.NetNodeFactory
import com.se.wmeditor.utils.jsToMap

fun NodeDescriptor.toMap(): Map<String, DefaultNodeModel> = jsToMap(this)

fun DiagramEngine.setup(): DiagramEngine {
    installDefaultFactories()
    registerNodeFactory(NetNodeFactory())
    val model = DiagramModel()
    model.addNode(NetNode("a", "net"))
    setDiagramModel(model)
    return this
}