package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.common.*
import com.se.wmeditor.home.diagram.nodes.DatasetNode
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*

class DatasetEditor(props: DatasetEditor.Props) : RComponent<DatasetEditor.Props, DatasetEditor.State>(props) {

  override fun State.init(props: Props) {
    selected = props.node.selectedDataset
  }

  override fun componentWillReceiveProps(nextProps: DatasetEditor.Props) {
    setState {
      selected = nextProps.node.selectedDataset
    }
  }

  override fun componentDidUpdate(prevProps: Props, prevState: State) {
    props.node.selectedDataset = state.selected
  }

  override fun RBuilder.render() {
    div("configurer-props") {
      div("configurer-props__group") {
        +"You should choose one of the available datasetMeta"
      }
      div("configurer-props__group") {
        b { +"Pretrained:" }
        select {
          attrs {
            name = "Test"
            onChangeFunction = { event ->
              val eventTarget = event.target.unsafeCast<HTMLOptionElement>().value.toUpperCase()
              setState {
                selected = props.node.datasets.find { eventTarget == it.type.name.toUpperCase() }
                  ?.let { DatasetMeta(it.type, it.state) }!!
              }
            }
          }
          props.node.datasets.forEach { dataset ->
            option {
              attrs {
                value = dataset.type.name
                label = dataset.label
                selected = dataset.type.name.toUpperCase() == state.selected.type.name.toUpperCase()
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
            checked = state.selected.state.inputSize[0] == 3
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
              value = state.selected.state.inputSize[1].toString()
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
              value = state.selected.state.inputSize[2].toString()
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
            value = state.selected.state.outputSize[0].toString()
            disabled = true
            onChangeFunction = { event ->
              val classNum = event.target.unsafeCast<HTMLInputElement>().value.toInt()
              setState {
                selected = selected.copy(state = DatasetState(selected.state.inputSize, listOf(classNum)))
              }
            }
          }
        }
      }
    }
  }

  private fun changeInput(index: Int, value: Int) {
    val prevState = state.selected.state
    val newInput = prevState.inputSize.mapIndexed { ind, el -> if (ind == index) value else el }
    val newState = DatasetState(newInput, prevState.outputSize)
    setState {
      selected = selected.copy(state = newState)
    }
  }

  interface Props : RProps {
    var node: DatasetNode
  }

  interface State : RState {
    var selected: DatasetMeta
  }
}

fun RBuilder.datasetEditor(handler: RHandler<DatasetEditor.Props>) =
  child(DatasetEditor::class, handler)
