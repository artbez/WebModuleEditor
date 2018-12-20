//package com.se.wmeditor.common
//
//
//enum class DatasetType {
//    NONE, IMAGENET, CIFAR10
//}
//
//enum class NetModelType {
//    VGG16
//}
//
//interface Description
//
//data class InputParams(var colored: Boolean, var width: Int, var height: Int)
//data class OutputParams(val numberClasses: Int)
//
//data class NetDescription(
//  val modelType: NetModelType,
//  val pretrained: DatasetType,
//  val inputParams: InputParams,
//  val outputParams: OutputParams
//) : Description {
//    override fun toString(): String {
//        return "[${modelType.name}, pretrained: ${pretrained.name}, input: $inputParams, output: $outputParams]"
//    }
//}
//
//data class DataDescription(val data: String) : Description {
//    override fun toString(): String {
//        return "[$data]"
//    }
//}
//
//data class DatasetDescription(
//  var datasetType: DatasetType,
//  val inputParams: InputParams = InputParams(true, 224, 224),
//  var outputParams: OutputParams? = null
//) : Description {
//    override fun toString(): String {
//        return "[datasetType: ${datasetType.name}]"
//    }
//}