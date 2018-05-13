package com.se.wmeditor.home

import com.se.wmeditor.home.diagram.node.*
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.DiagramModel


fun DiagramEngine.setup(): DiagramEngine {
    installDefaultFactories()
    registerNodeFactory(NetNodeFactory.instance)
    registerNodeFactory(NetTrainNodeFactory.instance)
    registerNodeFactory(NetEvalNodeFactory.instance)
    registerNodeFactory(UploadDatasetNodeFactory.instance)
    registerNodeFactory(AlertNodeFactory.instance)
    val model = DiagramModel()
    setDiagramModel(model)
    return this
}