package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.nodes.AlertNode
import react.*
import react.dom.div
import react.dom.span

class AlertEditor : RComponent<AlertEditor.Props, RState>() {

    override fun RBuilder.render() {

        div("configurer-props") {
            div("configurer-props__group") {
                span { +"After port get a data, it will be displayed in alert" }
            }
        }
    }

    interface Props : RProps {
        var node: AlertNode
    }
}

fun RBuilder.alertEditor(handler: RHandler<AlertEditor.Props>) =
    child(AlertEditor::class, handler)
