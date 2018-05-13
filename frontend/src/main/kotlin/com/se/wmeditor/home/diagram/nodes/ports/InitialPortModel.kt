package com.se.wmeditor.home.diagram.nodes.ports

import com.se.wmeditor.utils.toMap
import com.se.wmeditor.wrappers.react.diagrams.LinkModelListener
import com.se.wmeditor.wrappers.react.diagrams.PortWidget
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultLinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.LinkModel
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import com.se.wmeditor.wrappers.react.diagrams.models.PortModel
import com.se.wmeditor.wrappers.react.tippy.Tooltip
import react.*
import react.dom.div

enum class PortType {
    In, Out;

    var label = this.name.toLowerCase()
}

enum class PortPosition {
    Left, Right, Top, Bottom;

    val label = this.name.toLowerCase()
}

sealed class InitialPortModel(name: String, val type: PortType) : PortModel("$name (${type.label})", type.label) {

    val name = "$name (${type.label})"

    init {
        setMaximumLinks(1)
    }

    override fun createLinkModel(): LinkModel<out LinkModelListener>? = DefaultLinkModel()

    override fun canLinkToPort(port: InitialPortModel): Boolean =
        type != port.type && this::class.simpleName == port::class.simpleName && getNode() != port.getNode()
                && getLinks().toMap().size == 1 && port.getLinks().toMap().size == 1

}

class NetPortModel(name: String, type: PortType) : InitialPortModel(name, type)
class DatasetPortModel(name: String, type: PortType) : InitialPortModel(name, type)
class DataPortModel(name: String, type: PortType) : InitialPortModel(name, type)

class InitialPortModelWidget : RComponent<InitialPortModelWidget.Props, RState>() {

    override fun RBuilder.render() {

        div(
            "diagram-net__port " +
                    "diagram-net__port__${props.port.type.label} " +
                    "diagram-net__port__${props.position.label}"
        ) {

            Tooltip {
                attrs {
                    title = props.port.name
                    position = "down"
                    trigger = "mouseenter"
                    theme = "light"
                    animation = "none"
                    sticky = true
                    animateFill = false
                    unmountHTMLWhenHide = false
                    duration = 100
                }
                child(PortWidget::class) {
                    attrs {
                        this.node = props.node
                        this.name = props.port.name
                    }

                }
            }
        }

    }

    interface Props : RProps {
        var node: NodeModel
        var position: PortPosition
        var port: InitialPortModel
    }
}

fun RBuilder.portModelWidget(handler: RHandler<InitialPortModelWidget.Props>) =
    child(InitialPortModelWidget::class, handler)