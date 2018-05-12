package com.se.wmeditor.home

import com.se.wmeditor.header.header
import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.editor.editor
import com.se.wmeditor.home.diagram.palette.palette
import com.se.wmeditor.home.diagram.scene
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import kotlinext.js.invoke
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class HomeComponent : RComponent<RProps, RState>() {

    private lateinit var engine: DiagramEngine
    private lateinit var paletteSceneTransferObject: PaletteSceneTransferObject

    companion object {
        init {
            kotlinext.js.require("styles/home.scss")
        }
    }

    override fun componentWillMount() {
        paletteSceneTransferObject = PaletteSceneTransferObject()
        engine = DiagramEngine().setup()
    }

    override fun RBuilder.render() {
        header
        div("row home-all") {
            div("col-md-2") {
                editor {
                    attrs {
                        engine = this@HomeComponent.engine
                        updateDiagram = { forceUpdate {} }
                    }
                }
            }
            div("col-md-7") {
                scene {
                    attrs {
                        paletteSceneTransfer = paletteSceneTransferObject
                        engine = this@HomeComponent.engine
                        updateDiagram = { forceUpdate {} }
                    }
                }
            }
            div("col-md-3") {
                palette {
                    attrs {
                        paletteSceneTransfer = paletteSceneTransferObject
                    }
                }

            }
        }
    }
}