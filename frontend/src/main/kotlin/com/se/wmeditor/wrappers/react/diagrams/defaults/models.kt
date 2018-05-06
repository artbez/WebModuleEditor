@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.defaults

import com.se.wmeditor.utils.JsMap
import com.se.wmeditor.wrappers.react.diagrams.BaseEvent
import com.se.wmeditor.wrappers.react.diagrams.models.*

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

@JsName("DefaultLinkModelListener")
external interface DefaultLinkModelListener : LinkModelListener {

    fun <T> colorChanged(event: T) where T : BaseEvent<DefaultLinkModel>, T : ColorHave

    fun <T> widthChanged(event: T) where T : BaseEvent<DefaultLinkModel>, T : WidthHave

    interface ColorHave {
        var color: String?
    }

    interface WidthHave {
        var width: Double
    }
}

@JsName("DefaultLinkModel")
open external class DefaultLinkModel(type: String = definedExternally) : LinkModel<DefaultLinkModelListener> {
    fun setWidth(width: Double)
    fun setColor(color: String)
}