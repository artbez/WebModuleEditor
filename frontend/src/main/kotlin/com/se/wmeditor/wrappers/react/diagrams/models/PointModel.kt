@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.BaseModelListener
import com.se.wmeditor.wrappers.react.diagrams.LinkModelListener

open external class PointModel(link: LinkModel<LinkModelListener>, points: Point) :
    BaseModel<LinkModel<LinkModelListener>, BaseModelListener> {

    fun getLink(): LinkModel<LinkModelListener>
    fun updateLocation(points: Point)
    fun getX(): Double
    fun getY(): Double

    interface Point {
        var x: Double
        var y: Double
    }
}