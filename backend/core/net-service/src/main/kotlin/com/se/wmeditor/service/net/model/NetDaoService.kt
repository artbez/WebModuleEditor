package com.se.wmeditor.service.net.model

import com.mongodb.MongoClient
import com.mongodb.client.gridfs.GridFSBuckets
import com.se.wmeditor.common.*
import kotlinx.serialization.json.JSON
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.util.ModelSerializer
import java.io.*


class NetDaoService {

  private val mongoClient = MongoClient("localhost", 27017)
  private val database = mongoClient.getDatabase("test2")
  private val gridFSBucket = GridFSBuckets.create(database)

  fun save(trainedNetMeta: TrainedNetMeta, model: ComputationGraph) {

    if (find(trainedNetMeta) != null) return

    val bytes = ByteArrayOutputStream(1024).use { output ->
      ModelSerializer.writeModel(model, output, true)
      output.toByteArray()
    }
    val key = createKey(trainedNetMeta)
    gridFSBucket.uploadFromStream(key, BufferedInputStream(ByteArrayInputStream(bytes), 1024))
  }

  fun find(trainedNetMeta: TrainedNetMeta): ComputationGraph? {
    val key = createKey(trainedNetMeta)
    return gridFSBucket.find().find { it.filename == key }?.let {
      val bytes = ByteArrayOutputStream(1024).use { output ->
        gridFSBucket.downloadToStream(key, output)
        output.toByteArray()
      }
      ModelSerializer.restoreComputationGraph(BufferedInputStream(ByteArrayInputStream(bytes), 1024))
    }
  }

  fun findAllSeq(netMeta: NetMeta) =
    gridFSBucket.find()
      .map { it.filename }
      .map { JSON.parse(TrainedNetMeta.serializer(), it) }
      .filter { it.netMeta.type == netMeta.type }

  private fun createKey(trainedNetMeta: TrainedNetMeta) =
    JSON.stringify(TrainedNetMeta.serializer(), trainedNetMeta)
}