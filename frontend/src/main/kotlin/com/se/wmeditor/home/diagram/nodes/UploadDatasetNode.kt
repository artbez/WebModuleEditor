package com.se.wmeditor.home.diagram.nodes

import com.se.wmeditor.dom.uploadDatasetIcon
import com.se.wmeditor.home.diagram.nodes.ports.DatasetPortModel
import com.se.wmeditor.home.diagram.nodes.ports.PortPosition
import com.se.wmeditor.home.diagram.nodes.ports.PortType
import com.se.wmeditor.home.diagram.nodes.ports.portModelWidget
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class UploadDatasetNode : NodeModel(name, "") {

    val outputDatasetPort = DatasetPortModel("Dataset", PortType.Out)

    init {
        addPort(outputDatasetPort)
    }

    companion object {
        const val name = "upload_dataset"
    }
}

class UploadDatasetWidget : RComponent<UploadDatasetWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net__node") {
            uploadDatasetIcon {}
            if (props.isView != true) {
                portModelWidget {
                    attrs {
                        node = props.node
                        port = props.node.outputDatasetPort
                        position = PortPosition.Right
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: UploadDatasetNode
        var isView: Boolean?
    }
}

fun RBuilder.uploadDatasetWidget(handler: RHandler<UploadDatasetWidget.Props>) =
    child(UploadDatasetWidget::class, handler)


class UploadDatasetNodeFactory : AbstractNodeFactory<UploadDatasetNode>(UploadDatasetNode.name) {

    companion object {
        val instance = UploadDatasetNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): UploadDatasetNode = UploadDatasetNode()

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: UploadDatasetNode): ReactElement = buildElement {
        uploadDatasetWidget {
            attrs.node = node
        }
    }!!
}