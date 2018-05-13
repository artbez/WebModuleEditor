@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.LinkModelListener


@JsName("LinkModel")
open external class LinkModel<T : LinkModelListener>(
    linkType: String = definedExternally,
    id: String = definedExternally
) : BaseModel<DiagramModel, T> {

    fun isLastPoint(point: PointModel): Boolean
    fun getPointIndex(point: PointModel): Int
    fun getPointModel(id: String): PointModel?
    fun getPortForPoint(point: PointModel): PortModel?
    fun getPointForPort(portModel: PortModel): PointModel?
    fun getFirstPoint(): PointModel
    fun getLastPoint(): PointModel
    fun setSourcePort(port: PortModel)
    fun setTargetPort(port: PortModel)
    fun getSourcePort(): PortModel
    fun getTargetPort(): PortModel
    fun point(x: Double, y: Double): PointModel
    fun addLabel(label: LabelModel)
    fun getPoints(): Array<PointModel>
    fun setPoints(points: Array<PointModel>)
    fun removePoint(point: PointModel)
    fun removePointsBefore(point: PointModel)
    fun removePointsAfter(point: PointModel)
    fun removeMiddlePoints()
    fun <T : PointModel> addPoint(pointModel: T, index: Int = definedExternally): T
    fun generatePoint(x: Double = definedExternally, y: Double = definedExternally): PointModel
}
