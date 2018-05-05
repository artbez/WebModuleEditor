package com.se.wmeditor.home.diagram.palette

import com.se.wmeditor.home.diagram.Color
import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.PortType
import kotlinext.js.invoke
import react.*
import react.dom.div

class Palette : RComponent<Palette.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/palette-styles.scss")
        }
    }

    override fun RBuilder.render() {
        div("tray") {
            paletteNode {
                attrs {
                    name = "default"
                    type = PortType.IN
                    onDragStart = { props.paletteSceneTransfer.putDto(name, type, Color.GREEN) }
                    onDragEnd = { props.paletteSceneTransfer.cleanDto() }
                }
            }
            paletteNode {
                attrs {
                    name = "default"
                    type = PortType.OUT
                    onDragStart = { props.paletteSceneTransfer.putDto(name, type, Color.BLUE) }
                    onDragEnd = { props.paletteSceneTransfer.cleanDto() }
                }
            }
        }
    }

    interface Props : RProps {
        var paletteSceneTransfer: PaletteSceneTransferObject
    }
}

fun RBuilder.palette(handler: RHandler<Palette.Props>) = child(Palette::class, handler)