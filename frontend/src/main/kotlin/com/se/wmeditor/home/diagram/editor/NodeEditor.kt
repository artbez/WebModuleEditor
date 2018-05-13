package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.nodes.NetNode
import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import react.dom.h4
import react.dom.hr

class NodeEditor : RComponent<NodeEditor.Props, RState>() {

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
                +"Configurer"
            }
            hr("home-left__line") { }

            when (selectedNodes.size) {
                1 -> {
                    when (selectedNodes[0].getType()) {
                        NetNode.Companion.name ->
                            netFieldsEditor {
                                attrs {
                                    node = selectedNodes[0] as NetNode
                                    updateDiagram = { props.updateDiagram() }
                                }
                            }

                    }
                    button(classes = "btn-danger") {
                        attrs.onClickFunction = {
                            it.stopPropagation()
                            it.preventDefault()
                            selectedNodes[0].getPorts().toMap().forEach {
                                it.value.getLinks().toMap().forEach {
                                    props.engine.getDiagramModel().removeLink(it.value)
                                }
                            }
                            props.engine.getDiagramModel().removeNode(selectedNodes[0])
                            props.updateDiagram()
                        }
                        +"Remove"
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var engine: DiagramEngine
        var updateDiagram: () -> Unit
    }
}

fun RBuilder.editor(handler: RHandler<NodeEditor.Props>) = child(NodeEditor::class, handler)