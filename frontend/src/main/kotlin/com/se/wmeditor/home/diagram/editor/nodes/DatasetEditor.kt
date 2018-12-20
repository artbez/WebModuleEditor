package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.common.DatasetMeta
import com.se.wmeditor.common.DatasetType
import com.se.wmeditor.home.diagram.nodes.DatasetNode
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLOptionElement
import react.*
import react.dom.b
import react.dom.div
import react.dom.option
import react.dom.select

class DatasetEditor : RComponent<DatasetEditor.Props, RState>() {

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
              props.node.selectedDataset =
                props.node.datasets.find { eventTarget == it.type.name.toUpperCase() }
                  ?.let { DatasetMeta(it.type, it.state) }!!
            }
          }
          props.node.datasets.forEach { dataset ->
            option {
              attrs {
                value = dataset.type.name
                label = dataset.label
                selected = dataset.type.name.toUpperCase() == props.node.selectedDataset.type.name.toUpperCase()
              }
            }
          }
        }
      }
    }
  }

  interface Props : RProps {
    var node: DatasetNode
  }
}

fun RBuilder.datasetEditor(handler: RHandler<DatasetEditor.Props>) =
  child(DatasetEditor::class, handler)
