package com.se.wmeditor.home.diagram.palette.blocks

import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.node.NetEvalNodeFactory
import com.se.wmeditor.home.diagram.node.NetTrainNodeFactory
import com.se.wmeditor.home.diagram.node.netEvalWidget
import com.se.wmeditor.home.diagram.node.netTrainWidget
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
                    paletteSceneTransfer = props.paletteSceneTransfer
                    this.node = NetTrainNodeFactory.instance.getNewInstance(null)
                }
                netTrainWidget {
                    attrs {
                        this.node = node
                        isView = true
                    }
                }
            }

            paletteNode {
                attrs {
                    label = "Eval"
                    paletteSceneTransfer = props.paletteSceneTransfer
                    this.node = NetEvalNodeFactory.instance.getNewInstance(null)
                }
                netEvalWidget {
                    attrs {
                        this.node = NetEvalNodeFactory.instance.getNewInstance(null)
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