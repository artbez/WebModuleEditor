package com.se.wmeditor.home.diagram.nodes

import com.se.wmeditor.dom.netIcon
import com.se.wmeditor.home.diagram.nodes.ports.NetPortModel
import com.se.wmeditor.home.diagram.nodes.ports.PortPosition
import com.se.wmeditor.home.diagram.nodes.ports.PortType
import com.se.wmeditor.home.diagram.nodes.ports.portModelWidget
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetNode(val config: NetNodeConfig) : NodeModel(name, "") {

    val outputNetPort = NetPortModel("Net", PortType.Out)
    var dataset: Dataset = Dataset.NONE

    init {
        addPort(outputNetPort)
    }

    companion object {
        const val name = "net"
    }
}

enum class Dataset {
    NONE, IMAGENET, CIFAR10
}

sealed class NetNodeConfig(val model: String, val description: String)

object VGG16Config : NetNodeConfig(
    "VGG-16",
    "Given image → find object name in the image, It takes input image of size 224 * 244 * 3 (RGB image)"
)

class NetNodeWidget : RComponent<NetNodeWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net__node") {
            netIcon { }
            if (props.isView != true) {
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
        var node: NetNode
        var isView: Boolean?
    }
}

fun RBuilder.netNodeWidget(handler: RHandler<NetNodeWidget.Props>) = child(NetNodeWidget::class, handler)

class NetNodeFactory : AbstractNodeFactory<NetNode>(NetNode.name) {

    companion object {
        val instance = NetNodeFactory()
    }

    override fun getNewInstance(initialConfig: NetNodeConfig): NetNode {
        return NetNode(initialConfig)
    }

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: NetNode): ReactElement {
        return buildElement {
            netNodeWidget {
                attrs {
                    this.node = node
                }
            }
        }!!
    }

}