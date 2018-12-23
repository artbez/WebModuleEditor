package com.se.wmeditor.home.diagram.editor.nodes.configs

import com.se.wmeditor.common.*
import com.se.wmeditor.utils.post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.ButtonType
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import kotlinx.serialization.json.JSON as KJSON

class PretrainedSelector(props: Props) : RComponent<PretrainedSelector.Props, PretrainedSelector.State>(props) {

  override fun State.init(props: Props) {
    trainedNetMeta = props.trainedNetMeta
    data = emptyList()
    chosen = trainedNetMeta
  }

  override fun componentDidMount() {
    GlobalScope.launch {
      val response = post("/api/net/info/nets/pretrained", KJSON.stringify(NetMeta.serializer(), state.trainedNetMeta.netMeta))
      val data = KJSON.parse(PretrainedInfo.serializer(), response)
      setState {
        this.data = data.nets
      }
    }
  }

  override fun RBuilder.render() {
    div("list-group") {
      button(classes = "list-group-item ${active(TrainedNetMeta(state.trainedNetMeta.netMeta, emptyList()))}") {
        attrs {
          type = ButtonType.button
          onClickFunction = {
            props.onPretrainedChange(TrainedNetMeta(state.trainedNetMeta.netMeta, emptyList()))
            setState {
              chosen = TrainedNetMeta(state.trainedNetMeta.netMeta, emptyList())
            }
          }
        }
        +"None"
      }
      state.data.mapIndexed { index, net ->
        val current = "${net.netMeta.type}:${net.netMeta.state.inputSize}:${net.netMeta.state.outputSize}" +
          net.datasets
            .map { "${it.type.name}:${it.state.inputSize}" }
            .foldRight("") { acc, s -> "$s → $acc" }

        button(classes = "list-group-item ${active(net)}") {
          attrs {
            type = ButtonType.button
            onClickFunction = {
              props.onPretrainedChange(state.data[index])
              setState {
                chosen = net
              }
            }
          }
          +current
        }
      }
    }
  }

  interface Props : RProps {
    var trainedNetMeta: TrainedNetMeta
    var onPretrainedChange: (TrainedNetMeta) -> Unit
  }

  interface State : RState {
    var trainedNetMeta: TrainedNetMeta
    var data: List<TrainedNetMeta>
    var chosen: TrainedNetMeta
  }

  private fun active(meta: TrainedNetMeta) = if (state.chosen == meta) "active" else ""
}

fun RBuilder.selectPretrained(handler: RHandler<PretrainedSelector.Props>) = child(PretrainedSelector::class, handler)