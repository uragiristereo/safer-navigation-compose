package com.github.uragiristereo.safer.compose.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import com.github.uragiristereo.safer.compose.navigation.core.SncUtil
import com.github.uragiristereo.safer.compose.navigation.core.route
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: T,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.(T) -> Unit,
) {
    val klass = route::class as KClass<T>

    SncUtil.registerRoute(klass)

    composable<T>(
        deepLinks = deepLinks,
        content = { data ->
            content(this, data ?: route)
        },
    )
}

inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.(T?) -> Unit,
) {
    val klass = T::class

    SncUtil.registerRoute(klass)

    composable(
        route = klass.route,
        arguments = listOf(SncUtil.namedNavArg),
        deepLinks = deepLinks,
        content = { entry ->
            val data = remember(this) { SncUtil.getDataOrNull(klass, entry) }

            content(entry, data)
        },
    )
}

inline fun <reified A : NavRoute, reified B : NavRoute> NavGraphBuilder.navigation(
    startDestination: KClass<A>,
    route: KClass<B>,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    SncUtil.registerRoute(route)

    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(SncUtil.namedNavArg),
        deepLinks = deepLinks,
        builder = builder,
    )
}
