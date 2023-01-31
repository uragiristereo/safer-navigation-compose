package com.github.uragiristereo.safer.compose.navigation.animation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import com.github.uragiristereo.safer.compose.navigation.core.Serializer
import com.github.uragiristereo.safer.compose.navigation.core.Util
import com.github.uragiristereo.safer.compose.navigation.core.route
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: T,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    noinline content: @Composable NavBackStackEntry.(T) -> Unit,
) {
    val klass = route::class as KClass<T>

    Serializer.registerRoute(klass)

    composable(
        route = klass.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            val data = remember(entry) { Util.getDataOrNull(klass, entry) }

            content(entry, data ?: route)
        },
    )
}

@Suppress("UNUSED_PARAMETER", "UNCHECKED_CAST")
@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: T,
    disableDeserialization: Boolean,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    val klass = route::class as KClass<T>

    Serializer.registerRoute(klass)

    composable(
        route = klass.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            content(entry)
        },
    )
}

@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: KClass<T>,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    noinline content: @Composable NavBackStackEntry.(T?) -> Unit,
) {
    Serializer.registerRoute(route)

    composable(
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            val data = remember(entry) { Util.getDataOrNull(route, entry) }

            content(entry, data)
        },
    )
}

@Suppress("UNUSED_PARAMETER")
@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: KClass<T>,
    disableDeserialization: Boolean,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    Serializer.registerRoute(route)

    composable(
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            content(entry)
        },
    )
}

@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.navigation(
    startDestination: KClass<T>,
    route: KClass<T>,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}
