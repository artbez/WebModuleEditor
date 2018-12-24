package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.*

class MainEditor : RComponent<MainEditor.Props, RState>() {

  companion object {
    init {
      kotlinext.js.require("styles/configurer.scss")
    }
  }

  private var selectedNodes: List<NodeModel> = emptyList()

  override fun componentWillReceiveProps(nextProps: Props) {
    selectedNodes = nextProps.engine.getDiagramModel().getNodes().toMap().values.filter { it.isSelected() }
  }

  override fun RBuilder.render() {
    div("home-left") {
      h4("home-left__title") {
        +if (props.blocked) "Execution panel" else "Configurer"
      }
      hr("home-left__line") { }

      when {
        selectedNodes.size == 1 && !props.blocked -> {
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
              this.blocked = props.blocked
            }
          }
        }

        selectedNodes.size > 1 || props.blocked -> diagramEditor {
          attrs {
            this.selectedNodes = this@MainEditor.selectedNodes
            this.blockScene = { blocked -> props.blockScene(blocked) }
            this.blocked = props.blocked
          }
        }
      }
    }
  }

  interface Props : RProps {
    var engine: DiagramEngine
    var updateDiagram: () -> Unit
    var blockScene: (Boolean) -> Unit
    var blocked: Boolean
  }
}

fun RBuilder.mainEditor(handler: RHandler<MainEditor.Props>) = child(MainEditor::class, handler)