package com.github.uragiristereo.safer.compose.navigation.core

import kotlin.reflect.KClass

interface NavRoute

inline val KClass<out NavRoute>.route: String
    get() = this.java.name
        .lowercase()
        .replace(oldChar = '.', newChar = '/')
        .replace(oldChar = '$', newChar = '/') + when {
        withData -> ""
        else -> Util.DATA_FULL
    }

inline val KClass<out NavRoute>.withData: Boolean
    get() = !this.java.declaredFields.any {
        it.type == this.java && it.name == "INSTANCE"
    }

@get:JvmName("NavRouteGetRoute")
val NavRoute.route: String
    get() = this::class.route

@get:JvmName("NavRouteGetRouteNullable")
val NavRoute?.route: String?
    get() = this?.let { it::class.route }

internal fun NavRoute.parseData(): String {
    val encoded = Serializer.encode(value = this)

    return this::class.route
        .replaceFirst(
            oldValue = Util.DATA_BRACKETS,
            newValue = encoded,
        )
}
