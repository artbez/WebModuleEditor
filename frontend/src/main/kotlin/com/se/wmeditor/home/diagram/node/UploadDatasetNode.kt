package com.se.wmeditor.home.diagram.node

import com.se.wmeditor.dom.uploadDatasetIcon
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.PortWidget
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultLinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class UploadDatasetNode : NodeModel("upload_dataset", "") {
    init {
        addPort(UploadPortModel("net"))
        //addPort(PortModel("dataset"))
        //addPort(PortModel("trained_net"))
    }
}

class UploadPortModel(name: String) : PortModel(name) {
    override fun createLinkModel(): DefaultLinkModel = DefaultLinkModel()
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
                div("diagram-net__port") {
                    child(PortWidget::class) {
                        attrs {
                            this.node = this@UploadDatasetWidget.props.node
                            this.name = "net"
                        }
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


class UploadDatasetNodeFactory : AbstractNodeFactory<UploadDatasetNode>("upload_dataset") {

    companion object {
        val instance = UploadDatasetNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): UploadDatasetNode = UploadDatasetNode()

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: UploadDatasetNode): ReactElement =
        buildElement {
            uploadDatasetWidget {
                attrs.node = node
            }
        }!!

}