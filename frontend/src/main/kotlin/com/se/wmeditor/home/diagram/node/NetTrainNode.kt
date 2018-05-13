package com.se.wmeditor.home.diagram.node

import com.se.wmeditor.dom.netTrainIcon
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.PortWidget
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultLinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetTrainNode : NodeModel("net_train", "") {
    init {
        addPort(TrainPortModel("net"))
        //addPort(PortModel("dataset"))
        //addPort(PortModel("trained_net"))
    }
}

class TrainPortModel(name: String) : PortModel(name) {
    override fun createLinkModel(): DefaultLinkModel = DefaultLinkModel()
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
                div("diagram-net__port") {
                    child(PortWidget::class) {
                        attrs {
                            this.node = this@NetTrainWidget.props.node
                            this.name = "net"
                        }
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


class NetTrainNodeFactory : AbstractNodeFactory<NetTrainNode>("net_train") {

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