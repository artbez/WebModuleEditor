@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

import react.RProps
import react.RState
import react.React
import react.ReactElement

@JsName("DiagramEngine")
external class DiagramEngine {
    fun installDefaultFactories()
    fun setDiagramModel(model: DiagramModel)
}

@JsName("DiagramModel")
external class DiagramModel {
    fun addLink(link: LinkModel)
    fun addNode(node: DefaultNodeModel)
    fun setGridSize(size: Int)
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

