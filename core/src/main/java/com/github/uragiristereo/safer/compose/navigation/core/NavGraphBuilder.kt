package com.github.uragiristereo.safer.compose.navigation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlin.reflect.KClass

inline fun <reified T : NavRoute> NavGraphBuilder.dialog(
    route: T,
    deepLinks: List<NavDeepLink> = listOf(),
    dialogProperties: DialogProperties = DialogProperties(),
    noinline content: @Composable NavBackStackEntry.(T) -> Unit,
) {
    @Suppress("UNCHECKED_CAST")
    val klass = route::class as KClass<T>

    SncUtil.registerRoute(klass)

    dialog<T>(
        deepLinks = deepLinks,
        dialogProperties = dialogProperties,
        content = { data ->
            content(this, data ?: route)
        },
    )
}

inline fun <reified T : NavRoute> NavGraphBuilder.dialog(
    deepLinks: List<NavDeepLink> = listOf(),
    dialogProperties: DialogProperties = DialogProperties(),
    noinline content: @Composable NavBackStackEntry.(T?) -> Unit,
) {
    val klass = T::class

    SncUtil.registerRoute(klass)

    dialog(
        route = klass.route,
        arguments = listOf(SncUtil.namedNavArg),
        deepLinks = deepLinks,
        dialogProperties = dialogProperties,
        content = { entry ->
            val data = remember(this) { SncUtil.getDataOrNull(klass, entry) }

            content(entry, data)
        },
    )
}
