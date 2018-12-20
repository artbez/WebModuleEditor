//package com.se.wmeditor.home.diagram.editor.nodes.configs
//
//import com.se.wmeditor.common.DatasetType
//import com.se.wmeditor.common.NetModelType
//import kotlinx.html.InputType
//import kotlinx.html.js.onChangeFunction
//import react.RBuilder
//import react.dom.*
//
//fun pretrainedDatasets(netModelType: NetModelType): List<DatasetType> = when (netModelType) {
//  NetModelType.VGG16 -> listOf(DatasetType.NONE, DatasetType.CIFAR10, DatasetType.IMAGENET)
//  else -> listOf(DatasetType.NONE)
//}
//
//fun RBuilder.netConfig(netModelType: NetModelType) = when (netModelType) {
//  NetModelType.VGG16 -> VGG16ConfigComponent()
//  else -> TODO()
//}
//
//inline fun RBuilder.VGG16ConfigComponent() {
//  div("configurer-props__group") {
//    span { +"Num classes: " }
//    input {
//      attrs.onChangeFunction = {
//        console.log(it.target)
//      }
//      attrs.type = InputType.number
//    }
//  }
//  div("configurer-props__group") {
//    span { +"Colored" }
//    input {
//      attrs {
//        type = InputType.checkBox
//      }
//    }
//  }
//
//  div("configurer-props__group") {
//    span { +"Width" }
//    input {
//      attrs {
//        type = InputType.number
//      }
//    }
//  }
//  div("configurer-props__group") {
//    span { +"Height" }
//    input {
//      attrs {
//        type = InputType.number
//      }
//    }
//  }
//}
