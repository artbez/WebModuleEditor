@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.BaseEntity
import com.se.wmeditor.wrappers.react.diagrams.BaseListener
import com.se.wmeditor.wrappers.react.diagrams.BaseModelListener

external interface SelectionModel {
    var model: BaseModel<BaseEntity<BaseListener>, BaseModelListener>
    var initialX: Double
    var initialY: Double
}