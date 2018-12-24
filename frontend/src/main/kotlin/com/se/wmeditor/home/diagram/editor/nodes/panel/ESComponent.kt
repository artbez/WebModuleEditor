package com.se.wmeditor.home.diagram.editor.nodes.panel

import com.se.wmeditor.dom.logIcon
import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.wrappers.react.bootstrap.OverlayTrigger
import com.se.wmeditor.wrappers.react.bootstrap.Popover
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import react.*
import react.dom.button
import react.dom.div

class ESComponent : RComponent<ESComponent.Props, RState>() {
  override fun RBuilder.render() {
    div("execution-state-element") {
      div("node_item") {
        div("node_item__widget") {
          createWidget(props.state.node)
        }
        div("node_item__title ") {
          +labelFromType(props.state.node)
        }
      }
      div("complete-and-log") {
        div {
          if (props.state.completed) +"Completed" else +"Executing..."
        }
        div {
          if (props.state.logs != null) {
            OverlayTrigger {
              attrs {
                trigger = "click"
                placement = "right"
                overlay = buildElement {
                  Popover {
                    attrs {
                      id = "popover-select-pretrained"
                      title = "Logs"
                    }
                    props.state.logs!!.forEach {
                      div {
                        +it
                      }
                    }
                  }
                }!!
              }
              button(classes = "log-section") {
                logIcon("log-section__icon") { }
              }
            }
          }
        }
      }
    }
  }

  interface Props : RProps {
    var state: ExecutionState
  }

  fun RBuilder.createWidget(nodeModel: NodeModel) {
    when (nodeModel) {
      is NetInitNode -> netNodeWidget {
        attrs {
          this.node = NetNodeFactory.instance.getNewInstance(nodeModel.netDescription)
          isView = true
        }
      }
      is NetTrainNode -> netTrainWidget {
        attrs {
          this.node = NetTrainNodeFactory.instance.getNewInstance(null)
          isView = true
        }
      }
      is NetEvalNode -> netEvalWidget {
        attrs {
          this.node = NetEvalNodeFactory.instance.getNewInstance(null)
          isView = true
        }
      }
      is DatasetNode -> datasetWidget {
        attrs {
          this.node = DatasetNodeFactory.instance.getNewInstance(nodeModel.datasets)
          isView = true
        }
      }
      is AlertNode -> alertNodeWidget {
        attrs {
          this.node = AlertNodeFactory.instance.getNewInstance(null)
          isView = true
        }
      }
    }
  }

  fun labelFromType(nodeModel: NodeModel) =
    when (nodeModel) {
      is NetInitNode -> nodeModel.netDescription.label
      is NetTrainNode -> "Train"
      is NetEvalNode -> "Evaluate"
      is DatasetNode -> nodeModel.selectedDataset.type.name
      is AlertNode -> "Alert"
      else -> TODO()
    }

}

fun RBuilder.executionState(handler: RHandler<ESComponent.Props>) = child(ESComponent::class, handler)