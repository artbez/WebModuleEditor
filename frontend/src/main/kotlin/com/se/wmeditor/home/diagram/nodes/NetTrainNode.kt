package com.se.wmeditor.home.diagram.nodes

import com.se.wmeditor.dom.netTrainIcon
import com.se.wmeditor.home.diagram.nodes.ports.*
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetTrainNode : NodeModel(name, "") {

    val inputNetPort = NetPortModel("Net", PortType.In)
    val inputDatasetPort = DatasetPortModel("Dataset", PortType.In)
    val outputNetPort = NetPortModel("Net", PortType.Out)

    init {
        addPort(inputNetPort)
        addPort(inputDatasetPort)
        addPort(outputNetPort)
    }

    companion object {
        const val name = "net_train"
    }
}

class NetTrainWidget : RComponent<NetTrainWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net__node") {
            netTrainIcon()
            if (props.isView != true) {
                portModelWidget {
                    attrs {
                        node = props.node
                        port = props.node.inputDatasetPort
                        position = PortPosition.Bottom
                    }
                }
                portModelWidget {
                    attrs {
                        node = props.node
                        port = props.node.inputNetPort
                        position = PortPosition.Left
                    }
                }

                portModelWidget {
                    attrs {
                        node = props.node
                        port = props.node.outputNetPort
                        position = PortPosition.Right
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: NetTrainNode
        var isView: Boolean?
    }
}

fun RBuilder.netTrainWidget(handler: RHandler<NetTrainWidget.Props>) = child(NetTrainWidget::class, handler)


class NetTrainNodeFactory : AbstractNodeFactory<NetTrainNode>(NetTrainNode.name) {

    companion object {
        val instance = NetTrainNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): NetTrainNode = NetTrainNode()

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: NetTrainNode): ReactElement = buildElement {
        netTrainWidget {
            attrs.node = node
        }
    }!!

}