package com.se.wmeditor.home.diagram.nodes.executors

//import kotlinx.coroutines.experimental.async
import com.se.wmeditor.common.ContextHolder
import com.se.wmeditor.home.diagram.editor.nodes.panel.DiagramExecutionPanel
import com.se.wmeditor.home.inPort
import com.se.wmeditor.home.outLinks
import com.se.wmeditor.utils.get
import com.se.wmeditor.utils.post
import com.se.wmeditor.wrappers.react.diagrams.models.NodeModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JSON as KJSON

class ComputationGraph(val nodes: List<NodeModel>, panel: DiagramExecutionPanel) {

  private val executors: List<AbstractNodeExecutor>

  init {
    nodes.forEach { it.setSelected(false) }
    val executorMap = nodes.map { it.getID() to createExecutor(it, panel) }.toMap()
    nodes.forEach { sourceNode ->
      sourceNode.outLinks().forEach {
        val targetPort = it.inPort()
        val targetExecutor = executorMap[targetPort.getNode().getID()]
          ?: throw IllegalStateException("Node must have executor")
        val sourceExecutor = executorMap[sourceNode.getID()]!!
        sourceExecutor.attachPort(targetPort, targetExecutor)
      }
    }
    executors = executorMap.values.toList()
  }

  suspend fun execute(callback: () -> Unit) {
    val contextId = getContextId()
    try {
      executors.map {
        it.contextId = contextId
        GlobalScope.launch { it.tryExecute() }
      }.forEach { it.join() }

    } finally {
      removeContext(contextId)
      callback()
    }
  }
}

suspend fun getContextId() = KJSON.parse(ContextHolder.serializer(), get("/api/net/context/create")).contextId

suspend fun removeContext(contextId: String) =
  post("/api/net/context/remove", KJSON.stringify(ContextHolder.serializer(), ContextHolder(contextId)))
