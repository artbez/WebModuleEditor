package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.nodes.executors.ComputationGraph
import com.se.wmeditor.home.neighbors
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.coroutines.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div

class DiagramEditor : RComponent<DiagramEditor.Props, RState>() {

  private fun selectClicked(e: Event) {
    props.selectedNodes[0].selectAllNodes()
  }

  private fun NodeModel.selectAllNodes(): List<NodeModel> {
    val nearNodes = neighbors()
      .filterNot { it.isSelected() }
      .map { it.also { it.setSelected(true) } }

    return nearNodes.plus(this).plus(nearNodes.flatMap { it.selectAllNodes() }).distinctBy { it.getID() }
  }


  private fun executeDiagram(e: Event) {
    e.preventDefault()
    e.stopPropagation()

    val allNodes = props.selectedNodes.flatMap { it.selectAllNodes() }
    val computationGraph = ComputationGraph(allNodes)
    GlobalScope.launch {
      props.blockScene(true)
      computationGraph.execute {
        props.blockScene(false)
      }
    }
  }

  override fun RBuilder.render() {
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
            onClickFunction = ::executeDiagram
          }
          +"Run"
        }
        div {
          +"Execute diagram"
        }
      }
    }

  }

  interface Props : RProps {
    var selectedNodes: List<NodeModel>
    var blockScene: (Boolean) -> Unit
  }
}

fun RBuilder.diagramEditor(handler: RHandler<DiagramEditor.Props>) = child(DiagramEditor::class, handler)