package com.se.wmeditor.home.diagram.editor.nodes.panel

import com.se.wmeditor.home.diagram.nodes.executors.ComputationGraph
import com.se.wmeditor.home.neighbors
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

class DEPComponent : RComponent<DEPComponent.Props, DEPComponent.State>() {

  override fun componentDidMount() {
    val diagramPanel = DiagramExecutionPanel { updatePanel(it) }
    executeDiagram(diagramPanel)
  }

  override fun State.init() {
    nodes = emptyList()
    done = false
  }

  private fun updatePanel(nodes: List<ExecutionState>) {
    if (nodes.all { it.completed }) {
      setState {
        done = true
        this.nodes = nodes
      }
    } else {
      setState {
        this.nodes = nodes
      }
    }
  }

  private fun NodeModel.selectAllNodes(): List<NodeModel> {
    val nearNodes = neighbors()
      .filterNot { it.isSelected() }
      .map { it.also { it.setSelected(true) } }

    return nearNodes.plus(this).plus(nearNodes.flatMap { it.selectAllNodes() }).distinctBy { it.getID() }
  }


  private fun executeDiagram(panel: DiagramExecutionPanel) {
    props.blockScene(true)
    val allNodes = props.selectedNodes.flatMap { it.selectAllNodes() }
    val computationGraph = ComputationGraph(allNodes, panel)
    GlobalScope.launch {
      computationGraph.execute {}
    }
  }

  override fun RBuilder.render() {
    div("execution-panel-block") {
      state.nodes.forEach {
        executionState {
          attrs {
            state = it
          }
        }
        hr("palette__block__line") { }
      }
      if (state.done) {
        button(classes = "editor_button btn-primary") {
          attrs {
            onClickFunction = {
              it.preventDefault()
              it.stopPropagation()
              props.blockScene(false)
            }
          }
          +"Ok"
        }
      }
    }
  }

  interface Props : RProps {
    var blockScene: (Boolean) -> Unit
    var selectedNodes: List<NodeModel>
  }

  interface State : RState {
    var nodes: List<ExecutionState>
    var done: Boolean
  }
}

fun RBuilder.diagramPanel(handler: RHandler<DEPComponent.Props>) = child(DEPComponent::class, handler)