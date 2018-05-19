package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div

class DiagramEditor : RComponent<DiagramEditor.Props, RState>() {

    private fun selectClicked(e: Event) {
        props.selectedNodes[0].selectAllNodes()
    }

    private fun NodeModel.selectAllNodes() {
        getPorts().toMap().values
            .flatMap { it.getLinks().toMap().values.flatMap { listOf(it.getTargetPort().getNode(), it.getSourcePort().getNode()) } }
            .filterNot { it.isSelected() }
            .forEach { it.setSelected(true); it.selectAllNodes() }
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
                        onClickFunction = ::selectClicked
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
    }
}

fun RBuilder.diagramEditor(handler: RHandler<DiagramEditor.Props>) = child(DiagramEditor::class, handler)