package com.se.wmeditor.home.diagram.node

import com.se.wmeditor.dom.netIcon
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.PortWidget
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultLinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetNode(name: String) : NodeModel("net", "") {

    init {
        addPort(NetPortModel("right"))
    }
}

class NetPortModel(name: String) : PortModel(name) {

    override fun createLinkModel(): DefaultLinkModel = DefaultLinkModel()
}

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
                div("diagram-net__port") {
                    child(PortWidget::class) {
                        attrs {
                            this.node = this@NetNodeWidget.props.node
                            this.name = "right"
                        }
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: NetNode
        var isView: Boolean?
        var size: Int?
    }
}

fun RBuilder.netNodeWidget(handler: RHandler<NetNodeWidget.Props>) = child(NetNodeWidget::class, handler)

class NetNodeFactory : AbstractNodeFactory<NetNode>("net") {

    companion object {
        val instance = NetNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): NetNode {
        return NetNode("net")
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