@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.BaseEntity
import com.se.wmeditor.wrappers.react.diagrams.BaseEvent
import com.se.wmeditor.wrappers.react.diagrams.BaseListener

external interface BaseModelListener : BaseListener {
    var selectionChanged: ((event: SelectedEvent) -> Unit)?
    var entityRemoved: ((event: BaseEvent<BaseModel<dynamic, dynamic>>) -> Unit)?

    interface Selected {
        var isSelected: Boolean
    }

    interface SelectedEvent : BaseEvent<BaseModel<dynamic, dynamic>>, Selected
}

@JsName("BaseModel")
open external class BaseModel<X : BaseEntity<dynamic>, T : BaseModelListener> : BaseEntity<T> {
    fun getParent(): X
    fun setParent(parent: X)
    fun getSelectedEntities(): Array<BaseModel<BaseEntity<dynamic>, T>>
    fun getType(): String
    fun isSelected(): Boolean
    fun setSelected(selected: Boolean = definedExternally)
    fun remove()
}
