package com.se.wmeditor.home.diagram.palette.blocks

import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.node.NetNode
import com.se.wmeditor.home.diagram.node.netNodeWidget
import com.se.wmeditor.home.diagram.palette.paletteNode
import react.*
import react.dom.div
import react.dom.h4
import react.dom.hr

class NetActionBlock : RComponent<NetActionBlock.Props, RState>() {
    override fun RBuilder.render() {
        div("palette__block") {
            h4("palette__block__title") {
                +"Nets actions"
            }

            hr("palette__block__line") { }

            paletteNode {
                attrs {
                    label = "Train"
                    onDragStart = { props.paletteSceneTransfer.putDto(NetNode::class) }
                    onDragEnd = { props.paletteSceneTransfer.cleanDto() }
                }
                netNodeWidget {
                    attrs {
                        node = NetNode("VGA16")
                        isView = true
                    }
                }
            }

            paletteNode {
                attrs {
                    label = "Eval"
                    onDragStart = { props.paletteSceneTransfer.putDto(NetNode::class) }
                    onDragEnd = { props.paletteSceneTransfer.cleanDto() }
                }
                netNodeWidget {
                    attrs {
                        node = NetNode("VGA16")
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

fun RBuilder.netActionBlock(handler: RHandler<NetActionBlock.Props>) =
    child(NetActionBlock::class, handler)