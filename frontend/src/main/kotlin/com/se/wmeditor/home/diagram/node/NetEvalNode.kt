package com.se.wmeditor.home.diagram.node

import com.se.wmeditor.dom.netEvalIcon
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.PortWidget
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultLinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class NetEvalNode : NodeModel("net_eval", "") {
    init {
        addPort(EvalPortModel("eval"))
        //addPort(PortModel("dataset"))
        //addPort(PortModel("trained_net"))
    }
}

class EvalPortModel(name: String) : PortModel(name) {
    override fun createLinkModel(): DefaultLinkModel = DefaultLinkModel()
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
                div("diagram-net__port") {
                    child(PortWidget::class) {
                        attrs {
                            this.node = this@NetEvalWidget.props.node
                            this.name = "eval"
                        }
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


class NetEvalNodeFactory : AbstractNodeFactory<NetEvalNode>("net_eval") {

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