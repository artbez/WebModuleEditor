package com.se.wmeditor.service.net.api

import com.se.wmeditor.common.ContextHolder
import com.se.wmeditor.service.net.core.NetContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/net/context")
class ContextController(private val netContextHolder: NetContextHolder) {

  @GetMapping("create")
  fun create(): ContextHolder {
    return ContextHolder(UUID.randomUUID().toString())
  }

  @PostMapping("remove")
  fun remove(@RequestBody context: ContextHolder) {
    netContextHolder.removeContext(context.contextId)
  }
}