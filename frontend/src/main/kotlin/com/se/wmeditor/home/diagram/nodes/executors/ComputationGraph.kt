package com.se.wmeditor.home.diagram.nodes.executors

import com.se.wmeditor.common.ContextHolder
import com.se.wmeditor.home.inPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.get
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.coroutines.*
import kotlinx.serialization.*
//import kotlinx.coroutines.experimental.async
import kotlinx.serialization.json.JSON as KJSON

class ComputationGraph(val nodes: List<NodeModel>) {

    private val executors: List<AbstractNodeExecutor>

    init {
        nodes.forEach { it.setSelected(false) }
        val executorMap = nodes.map { it.getID() to createExecutor(it) }.toMap()
        nodes.forEach { sourceNode ->
            sourceNode.outLinks().forEach {
                val targetPort = it.inPort()
                val targetExecutor = executorMap[targetPort.getNode().getID()] ?: throw IllegalStateException("Node must have executor")
                val sourceExecutor = executorMap[sourceNode.getID()]!!
                sourceExecutor.attachPort(targetPort, targetExecutor)
            }
        }
        executors = executorMap.values.toList()
    }

    suspend fun execute(callback: () -> Unit) {
        val contextId = getContextId()

        try {
            executors.forEach {
                it.contextId = contextId
                GlobalScope.launch { it.tryExecute() }
            }

        } finally {
            removeContext(contextId)
            callback()
        }
    }
}

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun getContextId() = KJSON.parse<ContextHolder>(get("/api/net/context/create")).contextId
@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun removeContext(contextId: String) =
    KJSON.parse<ContextHolder>(post("/api/net/context/remove", KJSON.stringify(ContextHolder(contextId)))).contextId