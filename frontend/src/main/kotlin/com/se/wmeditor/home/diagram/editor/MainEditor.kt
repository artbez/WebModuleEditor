package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.div
import react.dom.h4
import react.dom.h5
import react.dom.hr

class MainEditor : RComponent<MainEditor.Props, RState>() {

  companion object {
    init {
      kotlinext.js.require("styles/configurer.scss")
    }
  }

  private var selectedNodes: List<NodeModel> = emptyList()

  override fun componentWillReceiveProps(nextProps: Props) {
    selectedNodes = nextProps.engine.getDiagramModel().getNodes().toMap().values.filter { it.isSelected() }
    console.log(nextProps.engine.getDiagramModel().getNodes())
    console.log(selectedNodes)
    console.log("")
  }

  override fun RBuilder.render() {
    div("home-left") {
      h4("home-left__title") {
        +"Configurer"
      }
      hr("home-left__line") { }

      when {
        selectedNodes.size == 1 -> {
          nodeEditor {
            attrs {
              this.engine = props.engine
              this.updateDiagram = props.updateDiagram
              this.selectedNode = selectedNodes[0]
            }
          }
          hr { }
          h5("home-left__title") {
            +"Diagram configurer"
          }
          diagramEditor {
            attrs {
              this.selectedNodes = this@MainEditor.selectedNodes
              this.blockScene = { blocked -> props.blockScene(blocked) }
            }
          }
        }
        selectedNodes.size > 1 -> diagramEditor {
          attrs {
            this.selectedNodes = this@MainEditor.selectedNodes
            this.blockScene = { blocked -> props.blockScene(blocked) }
          }
        }
      }
    }
  }

  interface Props : RProps {
    var engine: DiagramEngine
    var updateDiagram: () -> Unit
    var blockScene: (Boolean) -> Unit
  }
}

fun RBuilder.mainEditor(handler: RHandler<MainEditor.Props>) = child(MainEditor::class, handler)