package com.se.wmeditor.wrappers.react.diagrams

import react.RBuilder
import react.RHandler

fun RBuilder.diagramWidget(handler: RHandler<DiagramWidgetProps>) = child(DiagramWidget::class, handler)