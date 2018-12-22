//package com.se.wmeditor.service.net
//
//import com.mongodb.MongoClient
//import com.mongodb.MongoClientURI
//import com.mongodb.ServerAddress
//
//import com.mongodb.client.MongoDatabase
//import com.mongodb.client.MongoCollection
//
//import org.bson.Document
//import java.util.Arrays
//import com.mongodb.Block
//
//import com.mongodb.client.MongoCursor
//import com.mongodb.client.gridfs.GridFSBuckets
//import com.mongodb.client.model.Filters.*
//import com.mongodb.client.result.DeleteResult
//import com.mongodb.client.model.Updates.*
//import com.mongodb.client.result.UpdateResult
//import com.se.wmeditor.common.DatasetType
//import com.se.wmeditor.service.net.model.vgg16
//import org.deeplearning4j.datasets.fetchers.DataSetType
//import org.deeplearning4j.datasets.iterator.impl.TinyImageNetDataSetIterator
//import org.deeplearning4j.nn.graph.ComputationGraph
//import org.deeplearning4j.util.ModelSerializer
//import org.deeplearning4j.zoo.PretrainedType
//import org.deeplearning4j.zoo.model.VGG16
//import java.io.*
//import java.util.ArrayList
//import java.io.IOException
//import java.io.PipedOutputStream
//import java.io.PipedInputStream
//
//
//fun main(vararg args: String) {
//  val mongoClient = MongoClient("localhost", 27017)
//  val database = mongoClient.getDatabase("admin")
//  val doc = Document("type", vgg16.type.name)
//    .append("label", vgg16.label)
//    .append("description", vgg16.description)
//  database.createCollection("123")
//
//  val net = VGG16.builder().build()
//  val model = net.initPretrained<ComputationGraph>(PretrainedType.CIFAR10)
//  val outputStream = ByteArrayOutputStream(1024)
//  ModelSerializer.writeModel(model, outputStream, true)
//  val gridFSBucket = GridFSBuckets.create(database)
//
//  val bytes = outputStream.toByteArray()
//  outputStream.close()
//  val objectId = gridFSBucket.uploadFromStream("123", BufferedInputStream(ByteArrayInputStream(bytes), 1024))
//
//  val ttt = ByteArrayOutputStream()
//  gridFSBucket.downloadToStream(objectId, ttt)
//
//  print(ttt.toString())
//  print(outputStream.toString())
//
//  netModel = when {
//    datasetType == null -> zooModel.init()
//
//    !netModelService.checkPretrained(netType, netState, datasetType) ->
//      throw IllegalArgumentException("Incorrect pretrained size")
//
//    datasetType == DatasetType.IMAGENET -> {
//      zooModel.initPretrained<ComputationGraph>(PretrainedType.IMAGENET)
//    }
//
//    datasetType == DatasetType.CIFAR10 ->
//      zooModel.initPretrained<ComputationGraph>(PretrainedType.CIFAR10)
//
//    else -> TODO()
//
//  } as ComputationGraph
//
//
//}
//
//val datasetIterator = when (datasetType) {
//  DatasetType.TINY_IMAGENET ->
//    TinyImageNetDataSetIterator(batchSize, datasetState.inputSize.subList(1, 3).toIntArray(), DataSetType.TRAIN)
//
//  DatasetType.CIFAR10 -> CifarDataSetIterator(batchSize, datasetState.inputSize.reversed().toIntArray())
//
//  else -> throw IllegalArgumentException("No such datasetType")
//}
//
//netModel.fit(datasetIterator)