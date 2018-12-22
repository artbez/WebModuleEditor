package com.se.wmeditor.home.diagram

import com.se.wmeditor.wrappers.react.diagrams.BaseModelListener
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.diagramWidget
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinext.js.invoke
import kotlinext.js.jsObject
import kotlinx.html.js.*
import org.w3c.dom.events.Event
import react.*
import react.dom.div

class Scene : RComponent<Scene.Props, RState>() {

  companion object {
    init {
      kotlinext.js.require("styles/scene.scss")
    }
  }

  override fun RBuilder.render() {
    div("scene") {
      div("scene__inner") {
        attrs {
          onDropFunction = { e ->
            props.engine.addNode(e)
            props.updateDiagram()
          }
          onDragOverFunction = { it.preventDefault() }
          onContextMenuFunction = { it.stopPropagation(); it.preventDefault() }
        }
        diagramWidget {
          attrs {
            deleteKeys = emptyArray()
            className = "srd-demo-canvas"
            diagramEngine = props.engine
            allowLooseLinks = false
            maxNumberPointsPerLink = 0
          }
        }
      }
    }
  }

  private fun DiagramEngine.addNode(e: Event): NodeModel? {
    val node = props.paletteSceneTransfer.getDto()
    val point = getRelativeMousePoint(e)

    node.setPosition(point.x, point.y)

    node.addListener(
      BaseModelListener().events {
        this.selectionChanged = { props.updateDiagram() }
      }
    )

    getDiagramModel().addNode(node)
    return node
  }

  interface Props : RProps {
    var engine: DiagramEngine
    var paletteSceneTransfer: PaletteSceneTransferObject
    var updateDiagram: () -> Unit
  }
}


fun RBuilder.scene(handler: RHandler<Scene.Props>) = child(Scene::class, handler)