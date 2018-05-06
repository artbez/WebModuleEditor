@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.BaseEntity
import com.se.wmeditor.wrappers.react.diagrams.BaseEvent
import com.se.wmeditor.wrappers.react.diagrams.BaseListener

external interface BaseModelListener : BaseListener {
    fun <T> selectionChanged(event: T) where T : BaseEvent<BaseModel<dynamic, dynamic>>, T : Selected
    fun entityRemoved(event: BaseEvent<BaseModel<dynamic, dynamic>>)

    interface Selected {
        var isSelected: Boolean
    }
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
