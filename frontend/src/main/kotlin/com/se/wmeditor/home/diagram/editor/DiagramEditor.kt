package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.editor.nodes.panel.diagramPanel
import com.se.wmeditor.home.neighbors
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div

class DiagramEditor : RComponent<DiagramEditor.Props, DiagramEditor.State>() {

  override fun State.init() {
    execution = false
  }

  private fun selectClicked(e: Event) {
    props.selectedNodes[0].selectAllNodes()
  }

  private fun NodeModel.selectAllNodes(): List<NodeModel> {
    val nearNodes = neighbors()
      .filterNot { it.isSelected() }
      .map { it.also { it.setSelected(true) } }

    return nearNodes.plus(this).plus(nearNodes.flatMap { it.selectAllNodes() }).distinctBy { it.getID() }
  }

  private fun startExecution(e: Event) {
    e.preventDefault()
    e.stopPropagation()
    setState {
      execution = true
    }
  }

  override fun RBuilder.render() {
    if (state.execution) {
      diagramPanel {
        attrs {
          this.blockScene = props.blockScene
          this.selectedNodes = props.selectedNodes
        }
      }
    } else {
      div("configurer-props") {
        div("configurer-props__group") {
          button(classes = "editor_button btn-primary") {
            attrs {
              onClickFunction = ::selectClicked
            }
            +"Select"
          }
          div {
            +"Select diagram"
          }
        }
        div("configurer-props__group") {
          button(classes = "editor_button btn-success") {
            attrs {
              onClickFunction = ::startExecution
            }
            +"Run"
          }
          div {
            +"Execute diagram"
          }
        }
      }
    }
  }

  interface State : RState {
    var execution: Boolean
  }

  interface Props : RProps {
    var selectedNodes: List<NodeModel>
    var blockScene: (Boolean) -> Unit
    var blocked: Boolean
  }
}

fun RBuilder.diagramEditor(handler: RHandler<DiagramEditor.Props>) = child(DiagramEditor::class, handler)