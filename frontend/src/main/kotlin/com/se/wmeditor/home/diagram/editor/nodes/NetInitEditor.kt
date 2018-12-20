package com.se.wmeditor.home.diagram.editor.nodes

import com.se.wmeditor.common.DatasetMeta
import com.se.wmeditor.home.diagram.nodes.NetInitNode
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLOptionElement
import react.*
import react.dom.*

class NetInitEditor : RComponent<NetInitEditor.Props, NetInitEditor.State>() {

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
              props.node.datasetMeta = selected?.let { DatasetMeta(it.type, it.state) }

              setState {
                pretrained = props.node.datasetMeta != null
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
//      if (!state.pretrained) {
//        netConfig(props.node.config.modelType)
//      }
    }
  }

  interface Props : RProps {
    var node: NetInitNode
    var updateDiagram: () -> Unit
  }

  interface State: RState {
    var pretrained: Boolean
  }
}

fun RBuilder.netFieldsEditor(handler: RHandler<NetInitEditor.Props>) = child(NetInitEditor::class, handler)