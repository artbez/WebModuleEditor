package com.se.wmeditor.service.net

import com.se.wmeditor.common.ContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/net/context")
class ContextApi(private val netContextHolder: NetContextHolder) {

  @GetMapping("create")
  fun create(): ContextHolder {
    return ContextHolder(UUID.randomUUID().toString())
  }

  @PostMapping("remove")
  fun remove(@RequestBody context: ContextHolder) {
    netContextHolder.removeContext(context.contextId)
  }
}