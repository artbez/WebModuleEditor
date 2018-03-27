package com.se.wmeditor.home.diagram.pallete

import react.*
import react.dom.div

class TrayWidget : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("tray") {
            children()
        }
    }
}

fun RBuilder.trayWidget(handler: RHandler<RProps>) = child(TrayWidget::class, handler)