package com.se.wmeditor.home.diagram.palette.blocks

import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.home.diagram.palette.paletteNode
import com.se.wmeditor.utils.get
import com.se.wmeditor.utils.post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON
import react.*
import react.dom.div
import react.dom.h4
import react.dom.hr

class NetArchitecturesBlock : RComponent<NetArchitecturesBlock.Props, NetArchitecturesBlock.State>() {

  override fun State.init() {
    nets = emptyList()
  }

  override fun componentWillMount() {
    GlobalScope.launch {
      val netResponse = JSON.parse(NetDescription.serializer().list, get("/api/net/info/nets/all"))
      setState {
        nets = netResponse
      }
    }
  }

  override fun RBuilder.render() {
    div("palette__block") {
      h4("palette__block__title") {
        +"Nets architectures"
      }
      hr("palette__block__line") { }
      state.nets.forEach { config ->
        paletteNode {
          attrs {
            label = config.label
            paletteSceneTransfer = props.paletteSceneTransfer
            this.nodeProducer = {  NetNodeFactory.instance.getNewInstance(config) }
          }
          netNodeWidget {
            attrs {
              this.node = NetNodeFactory.instance.getNewInstance(config)
              isView = true
            }
          }
        }
      }
    }
  }


  interface Props : RProps {
    var paletteSceneTransfer: PaletteSceneTransferObject
  }

  interface State: RState {
    var nets: List<NetDescription>
  }
}

fun RBuilder.netArchitecturesBlock(handler: RHandler<NetArchitecturesBlock.Props>) =
  child(NetArchitecturesBlock::class, handler)