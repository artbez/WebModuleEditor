package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.editor.nodes.*
import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div

class NodeEditor : RComponent<NodeEditor.Props, RState>() {

  override fun RBuilder.render() {
    when (props.selectedNode.getType()) {
      NetInitNode.name ->
        netInitEditor {
          attrs {
            node = props.selectedNode as NetInitNode
            updateDiagram = { props.updateDiagram() }
          }
        }
      NetTrainNode.name ->
        netTrainEditor {
          attrs {
            node = props.selectedNode as NetTrainNode
            updateDiagram = { props.updateDiagram() }
          }
        }
      NetEvalNode.name ->
        netEvalEditor {
          attrs {
            node = props.selectedNode as NetEvalNode
            updateDiagram = { props.updateDiagram() }
          }
        }
      UploadDatasetNode.name ->
        uploadDatasetEditor {
          attrs {
            node = props.selectedNode as UploadDatasetNode
          }
        }
      AlertNode.name ->
        alertEditor {
          attrs {
            node = props.selectedNode as AlertNode
          }
        }
      DatasetNode.name ->
        datasetEditor {
          attrs {
            node = props.selectedNode as DatasetNode
          }
        }

    }
    div {
      button(classes = "editor_button btn-danger") {
        attrs.onClickFunction = { event ->
          event.stopPropagation()
          event.preventDefault()
          props.selectedNode.getPorts().toMap().forEach { it ->
            it.value.getLinks().toMap().forEach {
              val source = it.value.getSourcePort()
              val target = it.value.getTargetPort()
              source.removeLink(it.value)
              target.removeLink(it.value)
              props.engine.getDiagramModel().removeLink(it.value)
            }
          }
          props.engine.getDiagramModel().removeNode(props.selectedNode)
          props.updateDiagram()
        }
        +"Remove"
      }
    }
  }

  interface Props : RProps {
    var engine: DiagramEngine
    var updateDiagram: () -> Unit
    var selectedNode: NodeModel
  }
}

fun RBuilder.nodeEditor(handler: RHandler<NodeEditor.Props>) = child(NodeEditor::class, handler)