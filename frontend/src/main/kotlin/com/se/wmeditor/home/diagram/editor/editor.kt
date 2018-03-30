package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.input

class NodeEditor : RComponent<NodeEditor.Props, RState>() {

    override fun componentWillMount() {
        //props.engine.getDiagramModel().g
    }

    override fun RBuilder.render() {
        input {

        }
    }

    interface Props : RProps {
        var engine: DiagramEngine
        var updateParent: () -> Unit
    }
}