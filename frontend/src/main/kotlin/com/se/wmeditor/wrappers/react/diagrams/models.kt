@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

external interface BaseModelListener {
    var selectionChanged: () -> Unit
}

@JsName("BaseModel")
open external class BaseModel {
    fun addListener(selectionChanged: () -> Unit): String
}

@JsName("LinkModel")
open external class LinkModel : BaseModel

@JsName("DefaultLinkModel")
external class DefaultLinkModel : LinkModel {
    fun addLabel(label: String)
}

@JsName("DefaultPortModel")
external class DefaultPortModel : BaseModel {
    fun link(other: DefaultPortModel): DefaultLinkModel
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

external class NodeDescriptor {
    operator fun get(id: String): DefaultNodeModel
}

@JsName("DiagramModel")
external class DiagramModel {
    fun addLink(link: LinkModel)
    fun addNode(node: DefaultNodeModel)
    fun setGridSize(size: Int)
    fun getNodes(): NodeDescriptor//JsObjectMap<DefaultNodeModel>
    fun forEach(modelFunction: (BaseModel) -> Unit)
}