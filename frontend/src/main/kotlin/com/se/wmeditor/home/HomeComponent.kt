package com.se.wmeditor.home

import com.se.wmeditor.wrappers.react.diagrams.DefaultNodeModel
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import com.se.wmeditor.wrappers.react.diagrams.DiagramModel
import com.se.wmeditor.wrappers.react.diagrams.diagramWidget
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class HomeComponent : RComponent<RProps, HomeState>() {

    private val engine: DiagramEngine =
        DiagramEngine()

    init {
        engine.installDefaultFactories()

        val model = DiagramModel()
        model.setGridSize(50);
        val node1 = DefaultNodeModel("Node 1", "rgb(0,192,255)")
        val node2 = DefaultNodeModel("Node 2", "rgb(192,255,0)")
        val port1 = node1.addOutPort("Out")
        val port2 = node2.addInPort("In")

        node1.setPosition(100, 100)
        val link1 = port1.link(port2)
        link1.addLabel("Hello World!")
        model.addNode(node1)
        model.addNode(node2)
        model.addLink(link1)
        engine.setDiagramModel(model)
        console.log(engine)
    }

    override fun componentDidMount() {}

    override fun RBuilder.render() {

        diagramWidget {
            attrs {
                className = "srd-demo-canvas"
                diagramEngine = engine
            }
        }

    }
}


interface HomeState : RState {
    var message: String
}
