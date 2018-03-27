package com.se.wmeditor.home.diagram.pallete

import kotlinx.html.Draggable
import kotlinx.html.draggable
import react.*
import react.dom.div

class TrayItemWidget : RComponent<TrayItemWidgetProps, RState>() {
    override fun RBuilder.render() {
        div("tray-item") {
            attrs {
                draggable = Draggable.htmlTrue
            }
            +props.name
        }
    }
}

interface TrayItemWidgetProps : RProps {
    var model: dynamic
    var color: String?
    var name: String
}

fun RBuilder.trayItemWidget(handler: RHandler<TrayItemWidgetProps>) = child(TrayItemWidget::class, handler)