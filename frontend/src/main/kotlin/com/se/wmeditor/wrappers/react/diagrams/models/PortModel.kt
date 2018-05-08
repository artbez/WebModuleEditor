@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.utils.JsMap

@JsName("PortModel")
open external class PortModel(
    name: String,
    type: String = definedExternally,
    id: String = definedExternally,
    maximumLinks: Int = definedExternally
) : BaseModel<NodeModel, BaseModelListener> {

    fun getNode(): NodeModel
    fun getName(): String
    fun getMaximumLinks(): Int
    fun setMaximumLinks(maximumLinks: Int)
    fun removeLink(link: LinkModel<LinkModelListener>)
    fun addLink(link: LinkModel<LinkModelListener>)
    fun getLinks(): JsMap<LinkModel<LinkModelListener>>
    open fun createLinkModel(): LinkModel<out LinkModelListener>?
    fun updateCoords(coords: Coordinates): Coordinates
    fun canLinkToPort(port: PortModel): Boolean

    interface Coordinates {
        var x: Double
        var y: Double
        var width: Double
        var height: Double
    }
}