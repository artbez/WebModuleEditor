package com.se.wmeditor.utils

import kotlinext.js.Object

fun <T : Any, S> jsToMap(obj: T): Map<String, S> {
    val resultMap = mutableMapOf<String, S>()
    for (key in Object.keys(obj)) {
        val value = obj.asDynamic()[key]
        resultMap[key] = value as S
    }
    return resultMap
}