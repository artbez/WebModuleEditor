package com.se.wmeditor.home.diagram.node

import com.se.wmeditor.dom.alertIcon
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.PortWidget
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultLinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class AlertNode(name: String) : NodeModel("alert", "") {

    init {
        addPort(AlertPortModel("right"))
    }
}

class AlertPortModel(name: String) : PortModel(name) {

    override fun createLinkModel(): DefaultLinkModel = DefaultLinkModel()
}

class AlertNodeWidget : RComponent<AlertNodeWidget.Props, RState>() {

    companion object {
        init {
            kotlinext.js.require("styles/custom-nodes.scss")
        }
    }

    override fun RBuilder.render() {
        div("diagram-net__node") {
            alertIcon { }
            if (props.isView != true) {
                div("diagram-net__port") {
                    child(PortWidget::class) {
                        attrs {
                            this.node = this@AlertNodeWidget.props.node
                            this.name = "right"
                        }
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: AlertNode
        var isView: Boolean?
        var size: Int?
    }
}

fun RBuilder.alertNodeWidget(handler: RHandler<AlertNodeWidget.Props>) = child(AlertNodeWidget::class, handler)

class AlertNodeFactory : AbstractNodeFactory<AlertNode>("alert") {

    companion object {
        val instance = AlertNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): AlertNode {
        return AlertNode("alert")
    }

    override fun generateReactWidget(diagramEngine: DiagramEngine, node: AlertNode): ReactElement {
        return buildElement {
            alertNodeWidget {
                attrs {
                    this.node = node
                }
            }
        }!!
    }

}