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
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import com.github.uragiristereo.safer.compose.navigation.core.NavRouteUtil
import com.github.uragiristereo.safer.compose.navigation.core.Serializer
import com.github.uragiristereo.safer.compose.navigation.core.namedNavArg
import com.github.uragiristereo.safer.compose.navigation.core.route
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

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
    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = route.route,
        arguments = listOf(namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            val data = remember(entry) {
                when (val data = entry.arguments?.getString("data")) {
                    null -> route

                    else -> Serializer.decode(data) ?: route
                }
            }

            content(entry, data)
        },
    )
}

@Suppress("UNUSED_PARAMETER")
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
    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = route.route,
        arguments = listOf(namedNavArg),
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
    val newRoute = route.java.getConstructor().newInstance()

    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = newRoute.route,
        arguments = listOf(namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = { entry ->
            val data = remember(entry) {
                NavRouteUtil.getDataOrNull<T>(newRoute, entry)
            }

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
    val newRoute = route.java.getConstructor().newInstance()

    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = newRoute.route,
        arguments = listOf(namedNavArg),
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
fun NavGraphBuilder.navigation(
    startDestination: NavRoute,
    route: NavRoute,
    deepLinks: List<NavDeepLink> = listOf(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navigation(
    startDestination: KClass<NavRoute>,
    route: KClass<NavRoute>,
    deepLinks: List<NavDeepLink> = listOf(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navigation(
    startDestination: NavRoute,
    route: KClass<NavRoute>,
    deepLinks: List<NavDeepLink> = listOf(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.navigation(
    startDestination: KClass<NavRoute>,
    route: NavRoute,
    deepLinks: List<NavDeepLink> = listOf(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(namedNavArg),
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}
