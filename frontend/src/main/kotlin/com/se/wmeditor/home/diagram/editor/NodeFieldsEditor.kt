package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultNodeModel
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div
import react.dom.input

class NodeFieldsEditor : RComponent<NodeFieldsEditor.Props, NodeFieldsEditor.State>() {

    override fun State.init() {
        currentValue = ""
    }

    override fun componentDidMount() {
        setState {
            currentValue = props.node.name
        }
    }

    private fun handleInputFieldChanged(e: Event) {
        e.stopPropagation()
        e.preventDefault()
        val value = e.target.unsafeCast<HTMLInputElement>().value
        setState {
            currentValue = value
        }
    }

    private fun enterName(e: Event) {
        e.preventDefault()
        val nameNewValue = state.currentValue
        if (nameNewValue.isNotBlank()) {
            props.node.name = nameNewValue
        }
        props.updateDiagram()
    }


    override fun componentWillReceiveProps(nextProps: Props) {
        setState {
            currentValue = nextProps.node.name
        }
    }

    override fun RBuilder.render() {

        div {
            input(type = InputType.text) {
                attrs {
                    value = state.currentValue
                    onChangeFunction = ::handleInputFieldChanged
                }
            }
            button {
                attrs {
                    onClickFunction = ::enterName
                }
                +"Submit"
            }
        }
    }

    interface State : RState {
        var currentValue: String
    }

    interface Props : RProps {
        var node: DefaultNodeModel
        var updateDiagram: () -> Unit
    }
}

fun RBuilder.nodeFieldsEditor(handler: RHandler<NodeFieldsEditor.Props>) = child(NodeFieldsEditor::class, handler)