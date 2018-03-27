package com.se.wmeditor.home

import com.se.wmeditor.header.header
import com.se.wmeditor.home.diagram.palette.trayItemWidget
import com.se.wmeditor.home.diagram.palette.trayWidget
import com.se.wmeditor.home.diagram.scene
import com.se.wmeditor.wrappers.react.diagrams.DiagramEngine
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class HomeComponent : RComponent<RProps, HomeState>() {

    private val engine: DiagramEngine = DiagramEngine()

    override fun RBuilder.render() {
        header
        div("row") {
            div("col-md-2") { }
            div("col-md-8") {
                scene { }
            }
            div("col-md-2") {
                trayWidget {
                    trayItemWidget {
                        attrs {
                            name = "heh"
                        }
                    }
//                    trayItemWidget {
//                        attrs {
//                            model = js("{ type: \"in\" }")
//                            name="In Node"
//                            color="rgb(192,255,0)"
//                        }
//                    }
                }
            }
        }
    }
}


interface HomeState : RState {
    var message: String
}
