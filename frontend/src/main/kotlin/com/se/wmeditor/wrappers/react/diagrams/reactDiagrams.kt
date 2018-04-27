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
}

external interface BaseModelListener {
    var selectionChanged: () -> Unit
}

@JsName("BaseModel")
open external class BaseModel {
    fun addListener(selectionChanged: () -> Unit): String
}


@JsName("DiagramModel")
external class DiagramModel {
    fun addLink(link: LinkModel)
    fun addNode(node: DefaultNodeModel)
    fun setGridSize(size: Int)
    fun getNodes(): NodeDescriptor//JsObjectMap<DefaultNodeModel>
    fun forEach(modelFunction: (BaseModel) -> Unit)
}

external class NodeDescriptor {
    operator fun get(id: String): DefaultNodeModel
}

@JsName("DefaultNodeModel")
external class DefaultNodeModel(name: String, color: String) : BaseModel {
    var name: String
    fun isSelected(): Boolean
    fun addOutPort(label: String): DefaultPortModel
    fun addInPort(label: String): DefaultPortModel
    fun setPosition(xPosition: Int, yPosition: Int)
}

@JsName("LinkModel")
open external class LinkModel : BaseModel

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
external class DefaultPortModel : BaseModel {
    fun link(other: DefaultPortModel): DefaultLinkModel
}

external class Point {
    val x: Int
    val y: Int
}