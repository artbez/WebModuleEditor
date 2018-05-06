package com.se.wmeditor.utils

import kotlinext.js.Object

external class JsMap<T> {
    operator fun get(key: String): T
    operator fun set(key: String, value: T)
}

fun <T> JsMap<T>.toMap(): Map<String, T> {
    val resultMap = mutableMapOf<String, T>()
    for (key in Object.keys(this)) {
        val value = this.asDynamic()[key]
        resultMap[key] = value as T
    }
    return resultMap
}