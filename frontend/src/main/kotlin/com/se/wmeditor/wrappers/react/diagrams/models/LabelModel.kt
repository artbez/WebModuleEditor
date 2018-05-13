@file:JsModule("storm-react-diagrams")

package com.se.wmeditor.wrappers.react.diagrams.models

import com.se.wmeditor.wrappers.react.diagrams.BaseModelListener
import com.se.wmeditor.wrappers.react.diagrams.LinkModelListener

open external class LabelModel(offsetX: Double, offsetY: Double) :
    BaseModel<LinkModel<LinkModelListener>, BaseModelListener> {

}