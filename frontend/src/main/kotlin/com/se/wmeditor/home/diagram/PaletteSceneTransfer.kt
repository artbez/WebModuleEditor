package com.se.wmeditor.home.diagram

import com.se.wmeditor.wrappers.react.diagrams.DefaultNodeModel

enum class PortType {
    IN, OUT
}

enum class Color {
    BLUE {
        override fun toRgb(): String = "rgb(0, 192, 255)"
    },
    GREEN {
        override fun toRgb(): String = "rgb(192, 255, 0)"
    };

    abstract fun toRgb(): String
}

class PaletteSceneTransferObject {
    private var transferNode: DefaultNodeModel? = null

    fun putDto(name: String, type: PortType, color: Color) {
        val newTransferDto = DefaultNodeModel(name, color.toRgb())
        when (type) {
            PortType.IN -> newTransferDto.addInPort("In")
            PortType.OUT -> newTransferDto.addOutPort("Out")
        }
        transferNode = newTransferDto
    }

    fun getDto(): DefaultNodeModel? = transferNode

    fun cleanDto() {
        transferNode = null
    }
}