package com.se.wmeditor.home.diagram.palette.blocks

import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.nodes.NetNodeFactory
import com.se.wmeditor.home.diagram.nodes.VGG16Config
import com.se.wmeditor.home.diagram.nodes.netNodeWidget
import com.se.wmeditor.home.diagram.palette.paletteNode
import react.*
import react.dom.div
import react.dom.h4
import react.dom.hr

class NetArchitecturesBlock : RComponent<NetArchitecturesBlock.Props, RState>() {

    override fun RBuilder.render() {
        div("palette__block") {
            h4("palette__block__title") {
                +"Nets architectures"
            }
            hr("palette__block__line") { }
            paletteNode {
                attrs {
                    label = "VGG-16"
                    paletteSceneTransfer = props.paletteSceneTransfer
                    this.node = NetNodeFactory.instance.getNewInstance(VGG16Config)
                }
                netNodeWidget {
                    attrs {
                        this.node = NetNodeFactory.instance.getNewInstance(VGG16Config)
                        isView = true
                    }
                }
            }
        }
    }


    interface Props : RProps {
        var paletteSceneTransfer: PaletteSceneTransferObject
    }
}

fun RBuilder.netArchitecturesBlock(handler: RHandler<NetArchitecturesBlock.Props>) =
    child(NetArchitecturesBlock::class, handler)