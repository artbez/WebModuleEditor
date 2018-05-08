package com.se.wmeditor.home.diagram

import com.se.wmeditor.wrappers.react.diagrams.BaseModelListenerImpl
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.defaults.DefaultNodeModel
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
                onDropFunction = { e ->
                    props.engine.addNode(e)
                    props.updateDiagram()
                }
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

    private fun DiagramEngine.addNode(e: Event): DefaultNodeModel? {
        val node = props.paletteSceneTransfer.getDto() ?: return null
        val point = getRelativeMousePoint(e)

        node.setPosition(point.x, point.y)

        node.addListener(
            BaseModelListenerImpl().events {
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