package com.github.uragiristereo.safer.compose.navigation.core

import kotlin.reflect.KClass

interface NavRoute {
    val route: String
}

val <T : NavRoute> KClass<T>.route: String
    get() = this.java.getConstructor().newInstance().route


internal fun NavRoute.parseData(): String {
    val encoded = Serializer.encode(value = this)

    return route.replaceFirst(oldValue = "{data}", newValue = encoded)
}
