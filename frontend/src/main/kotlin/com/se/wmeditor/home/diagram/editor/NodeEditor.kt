package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultNodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import react.*

class NodeEditor : RComponent<NodeEditor.Props, RState>() {

    private var selectedNodes: List<NodeModel> = emptyList()

    override fun componentWillReceiveProps(nextProps: Props) {
        selectedNodes = nextProps.engine.getDiagramModel().getNodes().toMap().values.filter { it.isSelected() }
    }

    override fun RBuilder.render() {
        when (selectedNodes.size) {
            1 -> nodeFieldsEditor {
                attrs {
                    node = selectedNodes[0] as DefaultNodeModel
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