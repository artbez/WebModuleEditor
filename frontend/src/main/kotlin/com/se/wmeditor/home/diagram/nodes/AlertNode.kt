package com.se.wmeditor.home.diagram.nodes

import com.se.wmeditor.dom.alertIcon
import com.se.wmeditor.home.diagram.nodes.ports.DataPortModel
import com.se.wmeditor.home.diagram.nodes.ports.PortPosition
import com.se.wmeditor.home.diagram.nodes.ports.PortType
import com.se.wmeditor.home.diagram.nodes.ports.portModelWidget
import com.se.wmeditor.wrappers.react.diagrams.AbstractNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import react.*
import react.dom.div

class AlertNode : NodeModel(name, "") {

    val dataInputPort = DataPortModel("Data", PortType.In)

    init {
        addPort(dataInputPort)
    }

    companion object {
        const val name = "alert"
    }
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
                portModelWidget {
                    attrs {
                        node = props.node
                        port = props.node.dataInputPort
                        position = PortPosition.Left
                    }
                }
            }
        }
    }

    interface Props : RProps {
        var node: AlertNode
        var isView: Boolean?
    }
}

fun RBuilder.alertNodeWidget(handler: RHandler<AlertNodeWidget.Props>) = child(AlertNodeWidget::class, handler)

class AlertNodeFactory : AbstractNodeFactory<AlertNode>(AlertNode.name) {

    companion object {
        val instance = AlertNodeFactory()
    }

    override fun getNewInstance(initialConfig: dynamic): AlertNode {
        return AlertNode()
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