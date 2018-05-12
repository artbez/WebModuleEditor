package com.se.wmeditor.home.diagram.palette

import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.palette.blocks.dataManipulationBlock
import com.se.wmeditor.home.diagram.palette.blocks.netActionBlock
import com.se.wmeditor.home.diagram.palette.blocks.netArchitecturesBlock
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
        div("palette") {
            netArchitecturesBlock {
                attrs.paletteSceneTransfer = props.paletteSceneTransfer
            }
            netActionBlock {
                attrs.paletteSceneTransfer = props.paletteSceneTransfer
            }
            dataManipulationBlock {
                attrs.paletteSceneTransfer = props.paletteSceneTransfer
            }
        }
    }

    interface Props : RProps {
        var paletteSceneTransfer: PaletteSceneTransferObject
    }
}

fun RBuilder.palette(handler: RHandler<Palette.Props>) = child(Palette::class, handler)