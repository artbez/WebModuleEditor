@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.defaults

import com.se.wmeditor.utils.JsMap
import com.se.wmeditor.wrappers.react.diagrams.DefaultLinkModelListener
import com.se.wmeditor.wrappers.react.diagrams.LinkModelListener
import com.se.wmeditor.wrappers.react.diagrams.models.LabelModel
import com.se.wmeditor.wrappers.react.diagrams.models.LinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel

@JsName("DefaultNodeModel")
open external class DefaultNodeModel(name: String = definedExternally, color: String = definedExternally) : NodeModel {
    var name: String;
    var color: String;
    var ports: JsMap<PortModel>

    fun addInPort(label: String): DefaultPortModel
    fun addOutPort(label: String): DefaultPortModel
    fun getInPorts(): Array<DefaultPortModel>
    fun getOutPorts(): Array<DefaultPortModel>
}

@JsName("DefaultPortModel")
open external class DefaultPortModel(
    isInput: Boolean,
    name: String,
    label: String? = definedExternally,
    id: String = definedExternally
) : PortModel {

    fun link(port: PortModel): LinkModel<LinkModelListener>
}

@JsName("DefaultLabelModel")
open external class DefaultLabelModel : LabelModel {
    fun setLabel(label: String)
}

@JsName("DefaultLinkModel")
open external class DefaultLinkModel(type: String = definedExternally) : LinkModel<DefaultLinkModelListener> {
    fun setWidth(width: Double)
    fun setColor(color: String)
}