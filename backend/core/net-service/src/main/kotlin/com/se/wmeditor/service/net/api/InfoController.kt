package com.se.wmeditor.service.net.api

import com.se.wmeditor.service.net.dataset.DatasetService
import com.se.wmeditor.service.net.model.NetModelService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/net/info")
class InfoController(
  val netModelService: NetModelService,
  val datasetService: DatasetService
) {

  @GetMapping("nets/all")
  fun allNets() = netModelService.all()

  @GetMapping("datasets/all")
  fun allDatasets() = datasetService.all()
}
