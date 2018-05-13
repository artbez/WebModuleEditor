package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.nodes.NetNode
import kotlinx.html.ButtonType
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*

class NetFieldsEditor : RComponent<NetFieldsEditor.Props, NetFieldsEditor.State>() {

    override fun State.init() {
        currentValue = ""
    }

    override fun componentDidMount() {
        setState {
            currentValue = ""//props.node.name
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
            // props.node.name = nameNewValue
        }
        props.updateDiagram()
    }


    override fun componentWillReceiveProps(nextProps: Props) {
        setState {
            currentValue = ""//nextProps.node.name
        }
    }

    override fun RBuilder.render() {

        div("configurer-props") {
            div("configurer-props__group") {
                label {
                    +"Net model"
                }
                label { +props.node.config.model }
            }
            div("configurer-props__group") {
                +props.node.config.description
            }
            div("configurer-props__group") {
                label { +"Pretrained" }
                button(classes = "btn btn-default dropdown-toggle", type = ButtonType.button) {
                    ul("dropdown-menu") {
                        li {
                            +"ImageNet"
                        }
                    }
                }
            }
//            input(type = InputType.text) {
//                attrs {
//                    value = state.currentValue
//                    onChangeFunction = ::handleInputFieldChanged
//                }
//            }
//            button {
//                attrs {
//                    onClickFunction = ::enterName
//                }
//                +"Submit"
//            }
        }
    }

    interface State : RState {
        var currentValue: String
    }

    interface Props : RProps {
        var node: NetNode
        var updateDiagram: () -> Unit
    }
}

fun RBuilder.netFieldsEditor(handler: RHandler<NetFieldsEditor.Props>) = child(NetFieldsEditor::class, handler)