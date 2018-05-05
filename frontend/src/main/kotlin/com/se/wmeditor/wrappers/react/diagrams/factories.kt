@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams

@JsName("AbstractFactory")
abstract external class AbstractFactory<out T>(name: String) {
    val type: String

    fun getType(): String

    abstract fun getNewInstance(initialConfig: dynamic): T
}

@JsName("AbstractNodeFactory")
abstract external class AbstractNodeFactory<T>(name: String) : AbstractFactory<T> {
    abstract fun generateReactWidget(diagramEngine: DiagramEngine, node: T): dynamic
}