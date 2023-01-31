package com.github.uragiristereo.safer.compose.navigation.core

import kotlin.reflect.KClass

interface NavRoute

@Suppress("UNCHECKED_CAST")
val <T : NavRoute> KClass<T>.route: String
    get() = (this as KClass<NavRoute>).parseRoute()

@Suppress("UNCHECKED_CAST")
val <T : NavRoute> KClass<T>.withData: Boolean
    get() = !Util.isClassAnObject(this as KClass<NavRoute>)

val NavRoute?.route: String?
    get() = this?.let { it::class.route }

internal fun KClass<NavRoute>.parseRoute(): String {
    val route = this.qualifiedName!!.lowercase()
        .replace(oldChar = '.', newChar = '/')
        .replace(oldChar = '&', newChar = '/')

    return route + when {
        Util.isClassAnObject(klass = this) -> ""
        else -> Util.DATA_FULL
    }
}

internal fun NavRoute.parseData(): String {
    val encoded = Serializer.encode(value = this)

    return this::class.route.replaceFirst(oldValue = Util.DATA_BRACKETS, newValue = encoded)
}
