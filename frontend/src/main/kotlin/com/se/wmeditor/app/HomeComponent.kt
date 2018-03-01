package com.se.wmeditor.app

import com.infowings.catalog.wrappers.reactRouter
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class HomeComponent : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        reactRouter.Switch {
            reactRouter.Route {
                attrs {
                    path = "/"
                    render = { reactRouter.Redirect { attrs.to = "/aspects" } }
                }
            }
        }
    }
}