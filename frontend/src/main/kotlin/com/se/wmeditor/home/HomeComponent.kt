package com.se.wmeditor.home

import com.se.wmeditor.header.header
import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.palette.palette
import com.se.wmeditor.home.diagram.scene
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.DiagramModel
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class HomeComponent : RComponent<RProps, RState>() {

    private lateinit var engine: DiagramEngine
    private lateinit var paletteSceneTransferObject: PaletteSceneTransferObject

    override fun componentWillMount() {
        paletteSceneTransferObject = PaletteSceneTransferObject()

        engine = DiagramEngine()
        engine.installDefaultFactories()
        val model = DiagramModel()
        engine.setDiagramModel(model)
    }

    override fun RBuilder.render() {
        header
        div("row") {
            div("col-md-2") { }
            div("col-md-8") {
                scene {
                    attrs {
                        paletteSceneTransfer = paletteSceneTransferObject
                        engine = this@HomeComponent.engine
                    }
                }
            }
            div("col-md-2") {
                palette {
                    attrs {
                        paletteSceneTransfer = paletteSceneTransferObject
                    }
                }
            }
        }
    }
}
