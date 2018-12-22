package com.se.wmeditor.service.net.core

import com.se.wmeditor.service.net.model.NetModelService
import java.util.concurrent.ConcurrentHashMap

class NetContextHolder(val netModelService: NetModelService) {
  private val contexts: MutableMap<String, NetActionService> = ConcurrentHashMap()

  fun getNetContext(contextId: String): NetActionService =
    contexts.computeIfAbsent(contextId) {
      NetActionService(netModelService)
    }

  fun removeContext(contextId: String) {
    contexts.remove(contextId)
  }
}