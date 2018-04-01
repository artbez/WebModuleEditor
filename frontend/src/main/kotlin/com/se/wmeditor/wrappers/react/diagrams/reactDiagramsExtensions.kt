package com.se.wmeditor.wrappers.react.diagrams

import com.se.wmeditor.utils.jsToMap

fun NodeDescriptor.toMap(): Map<String, DefaultNodeModel> = jsToMap(this)

fun DiagramEngine.setup(): DiagramEngine {
    installDefaultFactories()
    val model = DiagramModel()
    setDiagramModel(model)
    return this
}