package com.se.wmeditor.home

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1

class HomeComponent : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        h1 { +"Hello, world" }
    }
}
