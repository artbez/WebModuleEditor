package com.se.wmeditor.service.net

import com.se.wmeditor.common.ContextDatasetDesciption
import com.se.wmeditor.common.ContextNetDescription
import com.se.wmeditor.common.DataDescription
import com.se.wmeditor.common.NetDescription
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/net/actions")
class NetApi(private val netContextHolder: NetContextHolder) {

    @PostMapping("init")
    fun initNet(@RequestBody contextDescription: ContextNetDescription): NetDescription {
        val actual = netContextHolder.getNetContext(contextDescription.contextId)
        actual.createNet(contextDescription.netDescription)
        return contextDescription.netDescription
    }

    @PostMapping("eval")
    fun evalNet(@RequestBody contextDescription: ContextDatasetDesciption): DataDescription {
        val actual = netContextHolder.getNetContext(contextDescription.contextId)
        val accuracy = actual.evaluateNet(contextDescription.datasetDescription)
        return DataDescription(accuracy.toString())
    }
}