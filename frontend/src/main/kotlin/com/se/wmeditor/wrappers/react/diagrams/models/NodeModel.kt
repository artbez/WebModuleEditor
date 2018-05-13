@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.utils.JsMap
import com.se.wmeditor.wrappers.react.diagrams.BaseModelListener

@JsName("NodeModel")
open external class NodeModel(nodeType: String = definedExternally, id: String?) :
    BaseModel<DiagramModel, BaseModelListener> {

    fun setPosition(x: Double, y: Double)
    fun getPortFromID(id: String): PortModel?
    fun getPort(name: String): PortModel
    fun getPorts(): JsMap<PortModel>
    fun removePort(port: PortModel)
    fun <T : PortModel> addPort(port: T): T
    fun updateDimensions(dimension: Dimension): Dimension

    interface Dimension {
        var width: Double
        var height: Double
    }
}