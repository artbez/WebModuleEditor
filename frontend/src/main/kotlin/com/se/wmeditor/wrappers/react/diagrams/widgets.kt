@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

import react.RProps
import react.RState
import react.React
import react.ReactElement

@JsName("DiagramWidget")
external class DiagramWidget : React.Component<DiagramWidgetProps, RState> {
    override fun render(): ReactElement
}

external interface DiagramWidgetProps : RProps {
    var className: String
    var diagramEngine: DiagramEngine
}

@JsName("PortWidget")
external class Point {
    val x: Int
    val y: Int
}
