package com.se.wmeditor.home

import com.se.wmeditor.home.diagram.node.NetNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.DiagramModel


fun DiagramEngine.setup(): DiagramEngine {
    installDefaultFactories()
    registerNodeFactory(NetNodeFactory())
    val model = DiagramModel()
    setDiagramModel(model)
    return this
}