package com.se.wmeditor.service.net

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext

class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(ctx: GenericApplicationContext) = org.springframework.context.support.beans {
      bean<NetContextHolder>()
    }.initialize(ctx)
}