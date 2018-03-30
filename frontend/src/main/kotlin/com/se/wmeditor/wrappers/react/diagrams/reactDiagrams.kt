@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

import org.w3c.dom.events.Event
import react.RProps
import react.RState
import react.React
import react.ReactElement

@JsName("DiagramEngine")
external class DiagramEngine {
    fun installDefaultFactories()
    fun setDiagramModel(model: DiagramModel)
    fun getRelativeMousePoint(event: Event): Point
    fun getDiagramModel(): DiagramModel
    //fun getNodes(): Array<NodeModel>
}

external class Point {
    val x: Int
    val y: Int
}

@JsName("DiagramModel")
external class DiagramModel {
    fun addLink(link: LinkModel)
    fun addNode(node: DefaultNodeModel)
    fun setGridSize(size: Int)
}

//@JsName("NodeModel")
//external class NodeModel {
//
//}

@JsName("DefaultNodeModel")
external class DefaultNodeModel(name: String, color: String) {
    fun addOutPort(label: String): DefaultPortModel
    fun addInPort(label: String): DefaultPortModel
    fun setPosition(xPosition: Int, yPosition: Int)
}

@JsName("LinkModel")
open external class LinkModel

@JsName("DefaultLinkModel")
external class DefaultLinkModel : LinkModel {
    fun addLabel(label: String)
}

@JsName("DiagramWidget")
external class DiagramWidget : React.Component<DiagramWidgetProps, RState> {
    override fun render(): ReactElement
}

external interface DiagramWidgetProps : RProps {
    var className: String
    var diagramEngine: DiagramEngine
}

@JsName("DefaultPortModel")
external class DefaultPortModel {
    fun link(other: DefaultPortModel): DefaultLinkModel
}

