package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.common.NetState
import com.se.wmeditor.common.TrainedNetMeta
import com.se.wmeditor.dom.settingsIcon
import com.se.wmeditor.home.diagram.editor.nodes.configs.selectPretrained
import com.se.wmeditor.home.diagram.nodes.NetInitNode
import com.se.wmeditor.wrappers.react.bootstrap.OverlayTrigger
import com.se.wmeditor.wrappers.react.bootstrap.Popover
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

class NetInitEditor(props: NetInitEditor.Props) : RComponent<NetInitEditor.Props, NetInitEditor.State>(props) {

  override fun NetInitEditor.State.init(props: NetInitEditor.Props) {
    trainedNetMeta = props.node.trainedNetMeta
    initial = trainedNetMeta.copy()
  }

  override fun componentWillReceiveProps(nextProps: Props) {
    setState {
      trainedNetMeta = nextProps.node.trainedNetMeta
      initial = nextProps.node.trainedNetMeta.copy()
    }
  }

  override fun componentDidUpdate(prevProps: Props, prevState: State) {
    props.node.trainedNetMeta = state.trainedNetMeta
  }

  override fun componentWillUnmount() {
    console.log("here")
  }

  override fun RBuilder.render() {

    div("configurer-props") {
      div("configurer-props__group") {
        b {
          +"Net modelType:"
        }
        span { +props.node.netDescription.label }
      }
      div("configurer-props__group") {
        +props.node.netDescription.description
      }
      div("configurer-props__group") {
        b { +"Pretrained:" }
        OverlayTrigger {
          attrs {
            trigger = "click"
            placement = "right"
            overlay = buildElement {
              Popover {
                attrs {
                  id = "popover-select-pretrained"
                  title = "Chose pretrained"
                }
                selectPretrained {
                  attrs {
                    this.trainedNetMeta = state.trainedNetMeta
                    this.onPretrainedChange = { chosen ->
                      setState {
                        trainedNetMeta = chosen
                      }
                    }
                  }
                }
              }
            }!!
          }
          button(classes = "setting-section") {
            settingsIcon("setting-section__icon") { }
          }
        }
      }
      div("configurer-props__group") {
        span { +"Colored" }
        input {
          attrs {
            type = InputType.checkBox
            checked = state.trainedNetMeta.netMeta.state.inputSize[0] == 3
            onChangeFunction = { event ->
              val channels = event.target.asDynamic().checked as Boolean
              changeInput(0, if (channels) 3 else 1)
            }
          }
        }
      }
      div("configurer-props__group") {
        span { +"Image size" }
        div {
          input {
            attrs {
              type = InputType.number
              value = state.trainedNetMeta.netMeta.state.inputSize[1].toString()
              onChangeFunction = { event ->
                val height = event.target.unsafeCast<HTMLInputElement>().value.toInt()
                changeInput(1, height)
              }
            }
          }
          span { +"x" }
          input {
            attrs {
              type = InputType.number
              value = state.trainedNetMeta.netMeta.state.inputSize[2].toString()
              onChangeFunction = { event ->
                val width = event.target.unsafeCast<HTMLInputElement>().value.toInt()
                changeInput(2, width)
              }
            }
          }
        }
      }
      div("configurer-props__group") {
        span { +"Class numbers" }
        input {
          attrs {
            type = InputType.number
            value = state.trainedNetMeta.netMeta.state.outputSize[0].toString()
            onChangeFunction = { event ->
              val classNum = event.target.unsafeCast<HTMLInputElement>().value.toInt()
              val newState = NetState(state.trainedNetMeta.netMeta.state.inputSize, listOf(classNum))
              setState {
                trainedNetMeta = trainedNetMeta.copy(netMeta = trainedNetMeta.netMeta.copy(state = newState))
              }
            }
          }
        }
      }
    }
  }

  private fun changeInput(index: Int, value: Int) {
      val prevState = state.trainedNetMeta.netMeta.state
      val newInput = prevState.inputSize.mapIndexed { ind, el -> if (ind == index) value else el }
      val newState = NetState(newInput, prevState.outputSize)
      setState {
        trainedNetMeta = trainedNetMeta.copy(netMeta = trainedNetMeta.netMeta.copy(state = newState))
      }
  }

  interface Props : RProps {
    var node: NetInitNode
    var updateDiagram: () -> Unit
  }

  interface State : RState {
    var trainedNetMeta: TrainedNetMeta
    var initial: TrainedNetMeta
  }
}

fun RBuilder.netInitEditor(handler: RHandler<NetInitEditor.Props>) = child(NetInitEditor::class, handler)