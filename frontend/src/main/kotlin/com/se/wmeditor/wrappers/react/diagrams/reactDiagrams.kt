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
    fun registerNodeFactory(nodeFactory: AbstractNodeFactory<*>)
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
open
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

@JsName("PortWidget")
external class Point {
    val x: Int
    val y: Int
}

@JsName("AbstractFactory")
abstract external class AbstractFactory<out T>(name: String) {
    val type: String

    fun getType(): String

    abstract fun getNewInstance(initialConfig: dynamic): T
}

@JsName("AbstractNodeFactory")
abstract external class AbstractNodeFactory<T>(name: String) : AbstractFactory<T> {
    abstract fun generateReactWidget(diagramEngine: DiagramEngine, node: T): dynamic
}