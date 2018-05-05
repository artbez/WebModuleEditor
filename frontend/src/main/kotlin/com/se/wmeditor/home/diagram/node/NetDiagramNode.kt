package com.se.wmeditor.home.diagram.node

import com.se.wmeditor.dom.netIcon
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DefaultNodeModel
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetNode(name: String, val type: String) : DefaultNodeModel(name, "") {

    init {
        //addOutPort("net")
    }
}

class NetNodeWidget : RComponent<NetNodeWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net-node") {
            netIcon { }
        }
    }

    interface Props : RProps {
        var node: NetNode
        var size: Int?
    }
}

fun RBuilder.netNodeWidget(handler: RHandler<NetNodeWidget.Props>) = child(NetNodeWidget::class, handler)

class NetNodeFactory : AbstractNodeFactory<NetNode>("net") {
    override fun getNewInstance(initialConfig: dynamic): NetNode {
        return NetNode("nett", "t1")
    }

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: NetNode): dynamic {
        return buildElement {
            netNodeWidget {
                attrs {
                    this.node = node
                }
            }
        }
    }

}