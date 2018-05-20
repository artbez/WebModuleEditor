package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.home.diagram.nodes.NetEvalNode
import com.se.wmeditor.home.diagram.nodes.NetNode
import com.se.wmeditor.home.diagram.nodes.NetTrainNode
import com.se.wmeditor.utils.toMap
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.b
import react.dom.div
import react.dom.input
import react.dom.span

class NetTrainEditor : RComponent<NetTrainEditor.Props, RState>() {

    override fun RBuilder.render() {

        div("configurer-props") {
            div("configurer-props__group") {
                b {
                    +"In/Out net model:"
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
                +"Training Net model. Should has input net model and dataset with labels"
            }
        }
    }

    interface Props : RProps {
        var node: NetTrainNode
        var updateDiagram: () -> Unit
    }
}

fun NetTrainNode.inputNet(): String {
    val links = inputNetPort.getLinks().toMap()
    if (links.isEmpty()) {
        return "Not selected"
    }
    val sourceNode = links.values.toList()[0].getSourcePort().getNode()
    val targetNode = links.values.toList()[0].getTargetPort().getNode()

    val other = if (sourceNode.getID() == this.getID()) targetNode else sourceNode

    return when (other::class) {
        NetNode::class -> (other as NetNode).config.model.name
        NetTrainNode::class -> (other as NetTrainNode).inputNet()
        NetEvalNode::class -> (other as NetEvalNode).inputNet()
        else -> "Undefined"
    }
}

fun RBuilder.netTrainEditor(handler: RHandler<NetTrainEditor.Props>) = child(NetTrainEditor::class, handler)