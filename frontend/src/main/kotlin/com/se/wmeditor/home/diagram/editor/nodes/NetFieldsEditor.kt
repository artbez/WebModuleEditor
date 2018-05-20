package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.common.Dataset
import com.se.wmeditor.home.diagram.nodes.NetNode
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLOptionElement
import react.*
import react.dom.*

class NetFieldsEditor : RComponent<NetFieldsEditor.Props, RState>() {

    override fun RBuilder.render() {

        div("configurer-props") {
            div("configurer-props__group") {
                b {
                    +"Net model:"
                }
                span { +props.node.config.model.name }
            }
            div("configurer-props__group") {
                +props.node.config.description
            }
            div("configurer-props__group") {
                b { +"Pretrained:" }
                select {
                    attrs {
                        name = "Test"
                        onChangeFunction = {
                            props.node.dataset =
                                    Dataset.valueOf(it.target.unsafeCast<HTMLOptionElement>().value.toUpperCase())
                        }
                    }
                    option {
                        attrs {
                            value = "None"
                            label = ""
                        }
                    }
                    option {
                        attrs {
                            value = "ImageNet"
                            label = "ImageNet"
                        }
                    }
                    option {
                        attrs {
                            value = "CIFAR10"
                            label = "CIFAR10"
                        }
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: NetNode
        var updateDiagram: () -> Unit
    }
}

fun RBuilder.netFieldsEditor(handler: RHandler<NetFieldsEditor.Props>) = child(NetFieldsEditor::class, handler)