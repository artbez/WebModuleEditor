package com.se.wmeditor.service.net

import com.se.wmeditor.service.net.core.NetActionService
import com.se.wmeditor.service.net.core.NetContextHolder
import com.se.wmeditor.service.net.dataset.DatasetService
import com.se.wmeditor.service.net.model.NetDaoService
import com.se.wmeditor.service.net.model.NetModelService
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(ctx: GenericApplicationContext) = beans {
      bean<NetDaoService>()
      bean { NetModelService(ref()) }
      bean<DatasetService>()
      bean { NetContextHolder(ref()) }
      bean { NetActionService(ref()) }
    }.initialize(ctx)
}