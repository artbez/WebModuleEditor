package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.utils.toMap
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

class NetEvalEditor : RComponent<NetEvalEditor.Props, RState>() {

  override fun RBuilder.render() {
    div("configurer-props") {
      div("configurer-props__group") {
        b {
          +"In/Out net modelType:"
        }
        span { +props.node.inputNet() }
      }
      div("configurer-props__group") {
        b { +"Epoch number" }
        input(classes = "editor_input") {
          attrs {
            type = InputType.number
            value = props.node.epochNumber.toString()
            onChangeFunction = {
              props.node.epochNumber = it.target.unsafeCast<HTMLInputElement>().value.toInt()
              props.updateDiagram()
            }
          }
        }
      }
      div("configurer-props__group") {
        +"Evaluating Net modelType. Should has input net modelType and datasetMeta with labels"
      }
    }
  }

  interface Props : RProps {
    var node: NetEvalNode
    var updateDiagram: () -> Unit
  }
}

fun NetEvalNode.inputNet(): String {
  val links = inputNetPort.getLinks().toMap()
  if (links.isEmpty()) {
    return "Not selected"
  }
  val sourceNode = links.values.toList()[0].getSourcePort().getNode()
  val targetNode = links.values.toList()[0].getTargetPort().getNode()

  val other = if (sourceNode.getID() == this.getID()) targetNode else sourceNode

  return when (other::class) {
    NetInitNode::class -> (other as NetInitNode).netDescription.label
    NetTrainNode::class -> (other as NetTrainNode).inputNet()
    NetEvalNode::class -> (other as NetEvalNode).inputNet()
    else -> "Undefined"
  }
}

fun RBuilder.netEvalEditor(handler: RHandler<NetEvalEditor.Props>) = child(NetEvalEditor::class, handler)