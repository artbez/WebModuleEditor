package com.se.wmeditor.home.diagram.nodes

import com.se.wmeditor.dom.netEvalIcon
import com.se.wmeditor.home.diagram.nodes.ports.*
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetEvalNode : NodeModel(name, "") {

    val inputNetPort = NetPortModel("Net", PortType.In)
    val inputDatasetPort = DatasetPortModel("Dataset", PortType.In)
    val outputDataPort = DataPortModel("Data", PortType.Out)

    init {
        addPort(inputNetPort)
        addPort(inputDatasetPort)
        addPort(outputDataPort)
    }

    companion object {
        const val name = "net_eval"
    }
}

class NetEvalWidget : RComponent<NetEvalWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net__node") {
            netEvalIcon {}
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
                        port = props.node.outputDataPort
                        position = PortPosition.Right
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: NetEvalNode
        var isView: Boolean?
    }
}

fun RBuilder.netEvalWidget(handler: RHandler<NetEvalWidget.Props>) = child(NetEvalWidget::class, handler)


class NetEvalNodeFactory : AbstractNodeFactory<NetEvalNode>(NetEvalNode.name) {

    companion object {
        val instance = NetEvalNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): NetEvalNode = NetEvalNode()

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: NetEvalNode): ReactElement = buildElement {
        netEvalWidget {
            attrs.node = node
        }
    }!!

}