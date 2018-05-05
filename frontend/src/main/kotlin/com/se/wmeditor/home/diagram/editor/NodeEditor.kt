package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.wrappers.react.diagrams.DefaultNodeModel
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.toMap
import react.*

class NodeEditor : RComponent<NodeEditor.Props, RState>() {

    private var selectedNodes: List<DefaultNodeModel> = emptyList()

    override fun componentWillReceiveProps(nextProps: Props) {
        selectedNodes = nextProps.engine.getDiagramModel().getNodes().toMap().values.filter { it.isSelected() }
    }

    override fun RBuilder.render() {
        when (selectedNodes.size) {
            1 -> nodeFieldsEditor {
                attrs {
                    node = selectedNodes[0]
                    updateDiagram = { props.updateDiagram() }
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