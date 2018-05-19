package com.se.wmeditor.home.diagram.nodes

import com.se.wmeditor.dom.cabinetIcon
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

class DatasetNode : NodeModel(name, "") {

    val outputDatasetPort = DatasetPortModel("Dataset", PortType.Out)
    var dataset: Dataset = Dataset.IMAGENET

    init {
        addPort(outputDatasetPort)
    }

    companion object {
        const val name = "dataset"
    }
}

class DatasetWidget : RComponent<DatasetWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net__node") {
            cabinetIcon {}
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
        var node: DatasetNode
        var isView: Boolean?
    }
}

fun RBuilder.datasetWidget(handler: RHandler<DatasetWidget.Props>) =
    child(DatasetWidget::class, handler)


class DatasetNodeFactory : AbstractNodeFactory<DatasetNode>(DatasetNode.name) {

    companion object {
        val instance = DatasetNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): DatasetNode = DatasetNode()

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: DatasetNode): ReactElement =
        buildElement {
            datasetWidget {
                attrs.node = node
            }
        }!!

}