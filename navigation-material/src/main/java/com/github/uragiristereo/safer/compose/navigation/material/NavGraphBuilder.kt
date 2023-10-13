package com.github.uragiristereo.safer.compose.navigation.material

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import com.github.uragiristereo.safer.compose.navigation.core.SncUtil
import com.github.uragiristereo.safer.compose.navigation.core.route
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import kotlin.reflect.KClass

inline fun <reified T : NavRoute> NavGraphBuilder.bottomSheet(
    route: T,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.(T) -> Unit,
) {
    @Suppress("UNCHECKED_CAST")
    val klass = route::class as KClass<T>

    SncUtil.registerRoute(klass)

    bottomSheet<T>(
        deepLinks = deepLinks,
        content = { data ->
            content(this, data ?: route)
        },
    )
}

@OptIn(ExperimentalMaterialNavigationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.bottomSheet(
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.(T?) -> Unit,
) {
    val klass = T::class

    SncUtil.registerRoute(klass)

    bottomSheet(
        route = klass.route,
        arguments = listOf(SncUtil.namedNavArg),
        deepLinks = deepLinks,
        content = { entry ->
            val data = remember(entry) { SncUtil.getDataOrNull(klass, entry) }

            content(entry, data)
        },
    )
}
