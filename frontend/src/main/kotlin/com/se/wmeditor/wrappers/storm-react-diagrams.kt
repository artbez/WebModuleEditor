@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers

import react.RClass
import react.RProps

@JsName("DiagramEngine")
external class DiagramEngine {
    fun installDefaultFactories()
    fun setDiagramModel(model: DiagramModel)
}

@JsName("DiagramModel")
external class DiagramModel {
    fun addLink(link: LinkModel)
    fun addNode(node: DefaultNodeModel)
}

@JsName("DefaultNodeModel")
external class DefaultNodeModel(name: String, color: String) {
    fun addOutPort(port: String): DefaultPortModel
    fun addInPort(port: String): DefaultPortModel
    fun setPosition(xPosition: Int, yPosition: Int)
}

@JsName("LinkModel")
open external class LinkModel

@JsName("DefaultLinkModel")
external class DefaultLinkModel : LinkModel {
    fun addLabel(label: String)
}

@JsName("DiagramWidget")
external val DiagramWidget: RClass<DiagramWidgetProps> = definedExternally

external interface DiagramWidgetProps : RProps {
    var className: String
    var diagramEngine: DiagramEngine
}

@JsName("DefaultPortModel")
external class DefaultPortModel {
    fun link(other: DefaultPortModel): DefaultLinkModel
}

