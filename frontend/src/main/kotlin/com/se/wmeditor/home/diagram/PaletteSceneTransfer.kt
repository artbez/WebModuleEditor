package com.se.wmeditor.home.diagram

import com.se.wmeditor.home.diagram.node.NetNode
import com.se.wmeditor.home.diagram.node.NetNodeFactory
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlin.reflect.KClass

class PaletteSceneTransferObject {
    private var transferNode: NodeModel? = null

    fun <T : Any> putDto(clazz: KClass<T>) {
        transferNode = when (clazz) {
            NetNode::class -> NetNodeFactory.instance.getNewInstance(null)
            else -> throw IllegalStateException("There are no such node")
        }
    }

    fun getDto(): NodeModel = transferNode ?: throw IllegalStateException("There are no dto to transfer")

    fun cleanDto() {
        transferNode = null
    }
}