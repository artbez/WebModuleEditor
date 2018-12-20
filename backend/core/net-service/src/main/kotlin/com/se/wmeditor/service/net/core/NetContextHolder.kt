package com.se.wmeditor.service.net.core

import com.se.wmeditor.service.net.core.NetContext
import com.se.wmeditor.service.net.model.NetModelService
import java.util.concurrent.ConcurrentHashMap

class NetContextHolder(val netModelService: NetModelService) {
  private val contexts: MutableMap<String, NetContext> = ConcurrentHashMap()

  fun getNetContext(contextId: String): NetContext =
    contexts.computeIfAbsent(contextId) {
      NetContext(netModelService)
    }

  fun removeContext(contextId: String) {
    contexts.remove(contextId)
  }
}