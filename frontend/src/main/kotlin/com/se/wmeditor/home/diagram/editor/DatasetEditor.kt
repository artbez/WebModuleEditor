package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.nodes.Dataset
import com.se.wmeditor.home.diagram.nodes.DatasetNode
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLOptionElement
import react.*
import react.dom.b
import react.dom.div
import react.dom.option
import react.dom.select

class DatasetEditor : RComponent<DatasetEditor.Props, RState>() {

    override fun RBuilder.render() {

        div("configurer-props") {
            div("configurer-props__group") {
                +"You should choose one of the available dataset"
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
        var node: DatasetNode
    }
}

fun RBuilder.datasetEditor(handler: RHandler<DatasetEditor.Props>) =
    child(DatasetEditor::class, handler)
