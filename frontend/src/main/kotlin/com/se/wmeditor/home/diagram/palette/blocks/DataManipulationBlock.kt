package com.se.wmeditor.home.diagram.palette.blocks

import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.node.AlertNodeFactory
import com.se.wmeditor.home.diagram.node.UploadDatasetNodeFactory
import com.se.wmeditor.home.diagram.node.alertNodeWidget
import com.se.wmeditor.home.diagram.node.uploadDatasetWidget
import com.se.wmeditor.home.diagram.palette.paletteNode
import react.*
import react.dom.div
import react.dom.h4
import react.dom.hr

class DataManipulationBlock : RComponent<DataManipulationBlock.Props, RState>() {
    override fun RBuilder.render() {
        div("palette__block") {
            h4("palette__block__title") {
                +"Data manipulation"
            }

            hr("palette__block__line") { }

            paletteNode {
                attrs {
                    label = "Upload dataset"
                    paletteSceneTransfer = props.paletteSceneTransfer
                    this.node = UploadDatasetNodeFactory.instance.getNewInstance(null)
                }
                uploadDatasetWidget {
                    attrs {
                        node = UploadDatasetNodeFactory.instance.getNewInstance(null)
                        isView = true
                    }
                }
            }

            paletteNode {
                attrs {
                    label = "Alert"
                    paletteSceneTransfer = props.paletteSceneTransfer
                    this.node = AlertNodeFactory.instance.getNewInstance(null)
                }
                alertNodeWidget {
                    attrs {
                        this.node = AlertNodeFactory.instance.getNewInstance(null)
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

fun RBuilder.dataManipulationBlock(handler: RHandler<DataManipulationBlock.Props>) =
    child(DataManipulationBlock::class, handler)