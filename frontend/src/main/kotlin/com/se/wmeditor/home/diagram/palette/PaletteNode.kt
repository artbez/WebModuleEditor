package com.se.wmeditor.home.diagram.palette

import com.se.wmeditor.home.diagram.PortType
import kotlinx.html.Draggable
import kotlinx.html.draggable
import kotlinx.html.js.onDragEndFunction
import kotlinx.html.js.onDragStartFunction
import react.*
import react.dom.div

class PaletteNode : RComponent<PaletteNode.Props, RState>() {

    override fun RBuilder.render() {
        div("srd-default-node tray-item tray-item__${props.type.name.toLowerCase()}") {
            attrs {
                draggable = Draggable.htmlTrue
                onDragStartFunction = { props.onDragStart() }
                onDragEndFunction = { props.onDragEnd() }
            }
            div("srd-default-node__title ") {
                div("srd-default-node__name") {
                    +props.name
                }
            }
        }
    }

    interface Props : RProps {
        var name: String
        var type: PortType
        var onDragStart: () -> Unit
        var onDragEnd: () -> Unit
    }
}

fun RBuilder.paletteNode(handler: RHandler<PaletteNode.Props>) = child(PaletteNode::class, handler)