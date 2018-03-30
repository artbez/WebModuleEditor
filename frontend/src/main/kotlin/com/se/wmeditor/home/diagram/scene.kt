package com.se.wmeditor.home.diagram

import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.diagramWidget
import kotlinx.html.js.onDragOverFunction
import kotlinx.html.js.onDropFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.div

class Scene : RComponent<Scene.Props, RState>() {

    override fun RBuilder.render() {
        div("scene") {

            attrs {
                onDropFunction = { e -> props.engine.addNode(e); forceUpdate { } }
                onDragOverFunction = { it.preventDefault() }
            }

            diagramWidget {
                attrs {
                    className = "srd-demo-canvas"
                    diagramEngine = props.engine
                }
            }
        }
    }

    private fun DiagramEngine.addNode(e: Event) {
        val node = props.paletteSceneTransfer.getDto() ?: return
        val point = getRelativeMousePoint(e)
        node.setPosition(point.x, point.y)
        getDiagramModel().addNode(node)
    }

    interface Props : RProps {
        var engine: DiagramEngine
        var paletteSceneTransfer: PaletteSceneTransferObject
    }
}


fun RBuilder.scene(handler: RHandler<Scene.Props>) = child(Scene::class, handler)