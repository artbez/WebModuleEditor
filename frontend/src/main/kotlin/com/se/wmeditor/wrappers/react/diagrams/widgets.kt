@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

import react.RProps
import react.React
import react.ReactElement

@JsName("BaseWidget")
open external class BaseWidget<T : BaseWidgetProps> : React.Component<T, dynamic> {
    override fun render(): ReactElement
}

external interface BaseWidgetProps : RProps {
    var baseClass: String
    var className: String
    var extraProps: dynamic
}

@JsName("DiagramWidget")
external class DiagramWidget : BaseWidget<DiagramWidgetProps> {
    override fun render(): ReactElement
}

external interface DiagramWidgetProps : BaseWidgetProps {
    var diagramEngine: DiagramEngine
    var allowLooseLinks: Boolean
    var allowCanvasTranslation: Boolean
    var allowCanvasZoom: Boolean
    var inverseZoom: Boolean
    var maxNumberPointsPerLink: Int
    var smartRouting: Boolean

    var actionStartedFiring: (dynamic) -> Boolean
    var actionStillFiring: (dynamic) -> Unit
    var actionStoppedFiring: (dynamic) -> Unit

    var deleteKeys: Array<Int>
}

@JsName("PortWidget")
external class Point {
    val x: Double
    val y: Double
}
