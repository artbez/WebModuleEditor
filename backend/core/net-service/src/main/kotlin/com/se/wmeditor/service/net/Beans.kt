package com.se.wmeditor.service.net

import com.se.wmeditor.service.net.core.NetContextHolder
import com.se.wmeditor.service.net.dataset.DatasetService
import com.se.wmeditor.service.net.model.NetModelService
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(ctx: GenericApplicationContext) = beans {
      bean<NetModelService>()
      bean<DatasetService>()
      bean { NetContextHolder(ref()) }
    }.initialize(ctx)
}