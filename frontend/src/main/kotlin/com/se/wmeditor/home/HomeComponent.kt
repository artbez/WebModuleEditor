package com.se.wmeditor.home

import com.se.wmeditor.utils.post
import kotlinx.coroutines.experimental.launch
import react.*
import react.dom.h1
import kotlin.browser.window

class HomeComponent : RComponent<RProps, HomeState>() {

    private var timer: Int = 0

    override fun componentDidMount() {
        setState {
            message = "Try to ping..."
        }
        window.clearTimeout(timer)
        timer = window.setTimeout({
            launch {
                val answer = post("/api/echo", "ping successful")
                setState {
                    message = answer
                }
            }
        }, 5000)
    }

    override fun RBuilder.render() {
        h1 { +state.message }
    }
}

interface HomeState : RState {
    var message: String
}
