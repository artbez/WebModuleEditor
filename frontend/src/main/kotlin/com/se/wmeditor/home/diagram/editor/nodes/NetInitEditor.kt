package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.nodes.NetInitNode
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLOptionElement
import react.*
import react.dom.*

class NetInitEditor(props: NetInitEditor.Props) : RComponent<NetInitEditor.Props, NetInitEditor.State>(props) {

  override fun NetInitEditor.State.init(props: NetInitEditor.Props) {
    netState = props.node.netDescription.state
    pretrained = null
  }

  override fun componentDidUpdate(prevProps: NetInitEditor.Props, prevState: NetInitEditor.State) {
    props.node.datasetMeta = state.pretrained
    props.node.netDescription.state = state.netState
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
        select {
          attrs {
            name = "Test"
            onChangeFunction = { event ->
              val target = event.target.unsafeCast<HTMLOptionElement>().value.toUpperCase()
              val selected = props.node.netDescription.pretrained.find {
                it.type.name.toUpperCase() == target.toUpperCase()
              }
              setState {
                pretrained = selected?.let { DatasetMeta(it.type, it.state) }
              }
            }
          }
          option {
            attrs {
              value = "none"
              label = "None"
              selected = true
            }
          }
          props.node.netDescription.pretrained.forEach { dataset ->
            option {
              attrs {
                value = dataset.type.name
                label = dataset.label
                selected = props.node.datasetMeta?.type?.name?.toUpperCase() == value.toUpperCase()
              }
            }
          }
        }
      }
      div("configurer-props__group") {
        span { +"Colored" }
        input {
          attrs {
            type = InputType.checkBox
            checked = state.pretrained?.let { it.state.inputSize[0] == 3 } ?: state.netState.inputSize[0] == 3
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
              value = state.pretrained?.let { it.state.inputSize[1].toString() } ?: state.netState.inputSize[1].toString()
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
              value = state.pretrained?.let { it.state.inputSize[2].toString() } ?: state.netState.inputSize[2].toString()
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
            value = state.pretrained?.let { it.state.outputSize[0].toString() } ?: state.netState.outputSize[0].toString()
            onChangeFunction = { event ->
              val classNum = event.target.unsafeCast<HTMLInputElement>().value.toInt()
              val pretrained = state.pretrained
              if (pretrained != null) {
                val newState = pretrained.state.copy(outputSize = listOf(classNum))
                setState {
                  this.pretrained = pretrained.copy(state = newState)
                }
              } else {
                setState {
                  netState = state.netState.copy(outputSize = listOf(classNum))
                }
              }
            }
          }
        }
      }
    }
  }

  private fun changeInput(index: Int, value: Int) {
    val pretrained = state.pretrained
    if (pretrained != null) {
      val prevState = pretrained.state
      val newInput = prevState.inputSize.mapIndexed { ind, el -> if (ind == index) value else el }
      val newState = DatasetState(newInput, prevState.outputSize)
      setState {
        this.pretrained = pretrained.copy(state = newState)
      }
    } else {
      val prevState = state.netState
      val newInput = prevState.inputSize.mapIndexed { ind, el -> if (ind == index) value else el }
      val newState = NetState(newInput, prevState.outputSize)
      setState {
        this.netState = newState
      }
    }
  }

  interface Props : RProps {
    var node: NetInitNode
    var updateDiagram: () -> Unit
  }

  interface State : RState {
    var pretrained: DatasetMeta?
    var netState: NetState
  }
}

fun RBuilder.netFieldsEditor(handler: RHandler<NetInitEditor.Props>) = child(NetInitEditor::class, handler)