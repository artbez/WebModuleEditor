package com.se.wmeditor.home

import com.se.wmeditor.header.header
import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.editor.mainEditor
import com.se.wmeditor.home.diagram.palette.palette
import com.se.wmeditor.home.diagram.scene
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import kotlinext.js.invoke
import react.*
import react.dom.div

class HomeComponent : RComponent<RProps, HomeComponent.State>() {

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

  private fun setSceneBlocked(blocked: Boolean) {
    setState {
      this.blocked = blocked
      console.log("blocked:", blocked)
      if (blocked) {
        js("\$('.srd-node').css('pointer-events', 'none')")
        js("\$('.srd-default-link').css('pointer-events', 'none')")
      } else {
        js("\$('.srd-node').css('pointer-events', '')")
        js("\$('.srd-default-link').css('pointer-events', '')")
      }
      Unit
    }
  }

  override fun RBuilder.render() {
    header
    div("row home-all") {
      div("col-md-2") {
        mainEditor {
          attrs {
            engine = this@HomeComponent.engine
            updateDiagram = { forceUpdate {} }
            blockScene = { blocked -> setSceneBlocked(blocked) }
            blocked = state.blocked
          }
        }
      }
      div("main-scene col-md-7 blocked__${state.blocked}") {
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

  interface State : RState {
    var blocked: Boolean
  }
}