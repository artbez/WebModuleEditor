package com.se.wmeditor.common

import kotlinx.serialization.Serializable

@Serializable
data class ContextHolder(val contextId: String)

@Serializable
data class ContextNetDescription(val contextId: String, val netDescription: NetDescription)

@Serializable
data class ContextDatasetDesciption(val contextId: String, val datasetDescription: DatasetDescription)

@Serializable
data class ContextDataDescription(val contextId: String, val dataDescription: DataDescription)