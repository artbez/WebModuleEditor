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

class NetNode(name: String, val type: String) : NodeModel(name, "") {

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
            //console.log(props.node)
            div("diagram-net__port") {
                child(PortWidget::class) {
                    attrs {
                        this.node = this@NetNodeWidget.props.node
                        this.name = "right"
                    }
                    //console.log(this)
                }
            }
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