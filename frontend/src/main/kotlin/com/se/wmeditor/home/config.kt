package com.se.wmeditor.home

import com.se.wmeditor.home.diagram.node.NetNode
import com.se.wmeditor.home.diagram.node.NetNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.DiagramModel


fun DiagramEngine.setup(): DiagramEngine {
    installDefaultFactories()
    registerNodeFactory(NetNodeFactory())
    val model = DiagramModel()
    model.addNode(NetNode("a", "net"))
    setDiagramModel(model)
    return this
}