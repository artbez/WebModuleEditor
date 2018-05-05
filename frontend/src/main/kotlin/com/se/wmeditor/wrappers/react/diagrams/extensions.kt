package com.se.wmeditor.wrappers.react.diagrams

import com.se.wmeditor.utils.jsToMap
import react.RBuilder
import react.RHandler

fun NodeDescriptor.toMap(): Map<String, DefaultNodeModel> = jsToMap(this)

fun RBuilder.diagramWidget(handler: RHandler<DiagramWidgetProps>) = child(DiagramWidget::class, handler)