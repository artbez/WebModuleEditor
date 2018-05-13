package com.se.wmeditor.home.diagram

import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel

class PaletteSceneTransferObject {
    private var transferNode: NodeModel? = null

    fun putDto(node: NodeModel) {
        transferNode = node
    }

    fun getDto(): NodeModel = transferNode ?: throw IllegalStateException("There are no dto to transfer")

    fun cleanDto() {
        transferNode = null
    }
}