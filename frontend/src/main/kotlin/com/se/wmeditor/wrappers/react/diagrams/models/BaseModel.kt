@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.BaseEntity
import com.se.wmeditor.wrappers.react.diagrams.BaseModelListener

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
