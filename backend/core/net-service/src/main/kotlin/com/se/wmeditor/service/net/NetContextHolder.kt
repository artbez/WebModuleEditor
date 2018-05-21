package com.se.wmeditor.service.net

import java.util.concurrent.ConcurrentHashMap

class NetContextHolder {
    private val contexts: MutableMap<String, NetContext> = ConcurrentHashMap<String, NetContext>()

    fun getNetContext(contextId: String): NetContext =
        contexts.computeIfAbsent(contextId) {
            NetContext()
        }

    fun removeContext(contextId: String) {
        contexts.remove(contextId)
    }
}