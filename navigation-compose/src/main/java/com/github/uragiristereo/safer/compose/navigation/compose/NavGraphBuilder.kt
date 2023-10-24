package com.github.uragiristereo.safer.compose.navigation.compose

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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

inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: T,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    noinline popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    noinline content: @Composable NavBackStackEntry.(T) -> Unit,
) {
    @Suppress("UNCHECKED_CAST")
    val klass = route::class as KClass<T>

    SncUtil.registerRoute(klass)

    composable<T>(
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { data ->
            content(this, data ?: route)
        },
    )
}

inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    noinline popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    noinline content: @Composable NavBackStackEntry.(T?) -> Unit,
) {
    val klass = T::class

    SncUtil.registerRoute(klass)

    composable(
        route = klass.route,
        arguments = listOf(SncUtil.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            val data = remember(entry) { SncUtil.getDataOrNull(klass, entry) }

            content(entry, data)
        },
    )
}

inline fun <reified A : NavRoute, reified B : NavRoute> NavGraphBuilder.navigation(
    startDestination: KClass<A>,
    route: KClass<B>,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    noinline popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    SncUtil.registerRoute(route)

    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(SncUtil.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}
