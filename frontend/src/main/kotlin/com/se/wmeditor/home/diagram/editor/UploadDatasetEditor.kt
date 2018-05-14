package com.se.wmeditor.home.diagram.editor

import com.se.wmeditor.home.diagram.nodes.UploadDatasetNode
import kotlinx.html.InputType
import react.*
import react.dom.div
import react.dom.input
import react.dom.span

class UploadDatasetEditor : RComponent<UploadDatasetEditor.Props, RState>() {

    override fun RBuilder.render() {

        div("configurer-props") {
            div("configurer-props__group") {
                span { +"Upload 1 image or zip file with dataset" }
            }
            div("configurer-props__group") {
                input {
                    attrs {
                        type = InputType.file
                        name = "file"
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: UploadDatasetNode
    }
}

fun RBuilder.uploadDatasetEditor(handler: RHandler<UploadDatasetEditor.Props>) =
    child(UploadDatasetEditor::class, handler)
