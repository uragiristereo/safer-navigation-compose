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
import com.github.uragiristereo.safer.compose.navigation.core.SncUtil
import com.github.uragiristereo.safer.compose.navigation.core.route
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import kotlin.reflect.KClass

@OptIn(ExperimentalAnimationApi::class)
@Suppress("UNCHECKED_CAST")
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

@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
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
            val data = remember(this) { SncUtil.getDataOrNull(klass, entry) }

            content(entry, data)
        },
    )
}

@OptIn(ExperimentalAnimationApi::class)
inline fun <reified A : NavRoute, reified B : NavRoute> NavGraphBuilder.navigation(
    startDestination: KClass<A>,
    route: KClass<B>,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
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
