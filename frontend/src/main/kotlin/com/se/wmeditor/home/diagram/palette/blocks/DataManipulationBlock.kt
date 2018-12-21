package com.se.wmeditor.home.diagram.palette.blocks

import com.se.wmeditor.common.DatasetDescription
import com.se.wmeditor.common.NetDescription
import com.se.wmeditor.home.diagram.PaletteSceneTransferObject
import com.se.wmeditor.home.diagram.nodes.*
import com.se.wmeditor.home.diagram.palette.paletteNode
import com.se.wmeditor.utils.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON
import react.*
import react.dom.div
import react.dom.h4
import react.dom.hr

class DataManipulationBlock : RComponent<DataManipulationBlock.Props, DataManipulationBlock.State>() {

  override fun State.init() {
    datasets = emptyList()
  }

  @UseExperimental(ImplicitReflectionSerializer::class)
  override fun componentWillMount() {
    GlobalScope.launch {
      val netResponse = JSON.parse(DatasetDescription.serializer().list, get("/api/net/info/datasets/all"))
      setState {
        datasets = netResponse
      }
    }
  }

  override fun RBuilder.render() {
    div("palette__block") {
      h4("palette__block__title") {
        +"Data manipulation"
      }

      hr("palette__block__line") { }

      if (state.datasets.isNotEmpty()) {
        paletteNode {
          attrs {
            label = "Upload dataset"
            paletteSceneTransfer = props.paletteSceneTransfer
            this.node = UploadDatasetNodeFactory.instance.getNewInstance(null)
          }
          uploadDatasetWidget {
            attrs {
              node = UploadDatasetNodeFactory.instance.getNewInstance(null)
              isView = true
            }
          }
        }

        paletteNode {
          attrs {
            label = "Alert"
            paletteSceneTransfer = props.paletteSceneTransfer
            this.node = AlertNodeFactory.instance.getNewInstance(null)
          }
          alertNodeWidget {
            attrs {
              this.node = AlertNodeFactory.instance.getNewInstance(null)
              isView = true
            }
          }
        }

        paletteNode {
          attrs {
            label = "Existing dataset"
            paletteSceneTransfer = props.paletteSceneTransfer
            this.node = DatasetNodeFactory.instance.getNewInstance(state.datasets)
          }
          datasetWidget {
            attrs {
              this.node = DatasetNodeFactory.instance.getNewInstance(state.datasets)
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

  interface State : RState {
    var datasets: List<DatasetDescription>
  }
}

fun RBuilder.dataManipulationBlock(handler: RHandler<DataManipulationBlock.Props>) =
  child(DataManipulationBlock::class, handler)