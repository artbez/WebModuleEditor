package com.se.wmeditor.wrappers.react.diagrams

import com.se.wmeditor.wrappers.react.diagrams.models.BaseModel
import com.se.wmeditor.wrappers.react.diagrams.models.BaseModelListener
import kotlinext.js.toPlainObjectStripNull
import react.RBuilder
import react.RHandler

class BaseModelListenerImpl : BaseModelListener {
    override var selectionChanged: ((event: BaseModelListener.SelectedEvent) -> Unit)? = null
    override var lockChanged: ((event: BaseEvent<dynamic>, BaseListener.Locked) -> Unit)? = null
    override var entityRemoved: ((event: BaseEvent<BaseModel<dynamic, dynamic>>) -> Unit)? = null

    fun events(builder: BaseModelListenerImpl.() -> Unit): BaseModelListener {
        this.builder()
        return toPlainObjectStripNull(this)
    }
}

fun RBuilder.diagramWidget(handler: RHandler<DiagramWidgetProps>) = child(DiagramWidget::class, handler)