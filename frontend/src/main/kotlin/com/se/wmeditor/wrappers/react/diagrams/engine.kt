@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

import com.se.wmeditor.wrappers.react.diagrams.models.DiagramModel
import org.w3c.dom.events.Event

@JsName("DiagramEngine")
external class DiagramEngine {
    fun installDefaultFactories()
    fun setDiagramModel(model: DiagramModel)
    fun getRelativeMousePoint(event: Event): Point
    fun getDiagramModel(): DiagramModel
    fun registerNodeFactory(nodeFactory: AbstractNodeFactory<dynamic>)
}
