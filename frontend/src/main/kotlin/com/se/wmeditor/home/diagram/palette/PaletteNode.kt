package com.se.wmeditor.home.diagram.palette

import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.html.Draggable
import kotlinx.html.draggable
import kotlinx.html.js.onDragEndFunction
import kotlinx.html.js.onDragStartFunction
import react.*
import react.dom.div

class PaletteNode : RComponent<PaletteNode.Props, RState>() {

    override fun RBuilder.render() {
        div("node_item") {
            div("node_item__widget") {
                attrs {
                    draggable = Draggable.htmlTrue
                    onDragStartFunction = { props.paletteSceneTransfer.putDto(props.nodeProducer()) }
                    onDragEndFunction = { props.paletteSceneTransfer.cleanDto() }
                }
                this.children()
            }
            div("node_item__title ") {
                +props.label
            }
        }
    }

    interface Props : RProps {
        var label: String
        var nodeProducer: () -> NodeModel
        var paletteSceneTransfer: PaletteSceneTransferObject
    }
}

fun RBuilder.paletteNode(handler: RHandler<PaletteNode.Props>) = child(PaletteNode::class, handler)