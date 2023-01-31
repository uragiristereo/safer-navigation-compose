package com.github.uragiristereo.safer.compose.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import com.github.uragiristereo.safer.compose.navigation.core.Serializer
import com.github.uragiristereo.safer.compose.navigation.core.Util
import com.github.uragiristereo.safer.compose.navigation.core.route
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: T,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.(T) -> Unit,
) {
    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        content = { entry ->
            val data = remember(entry) {
                when (val data = entry.arguments?.getString(Util.DATA)) {
                    null -> route

                    else -> Serializer.decode(data) ?: route
                }
            }

            content(entry, data)
        },
    )
}

@Suppress("UNUSED_PARAMETER")
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: T,
    disableDeserialization: Boolean,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        content = { entry ->
            content(entry)
        },
    )
}

inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: KClass<T>,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.(T?) -> Unit,
) {
    val newRoute = route.java.getConstructor().newInstance()

    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = newRoute.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        content = { entry ->
            val data = remember(entry) {
                Util.getDataOrNull(route, entry)
            }

            content(entry, data)
        },
    )
}

@Suppress("UNUSED_PARAMETER")
inline fun <reified T : NavRoute> NavGraphBuilder.composable(
    route: KClass<T>,
    disableDeserialization: Boolean,
    deepLinks: List<NavDeepLink> = listOf(),
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    val newRoute = route.java.getConstructor().newInstance()

    Serializer.addPolymorphicType(name = T::class.qualifiedName!!) {
        subclass(T::class, serializer())
    }

    composable(
        route = newRoute.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        content = { entry ->
            content(entry)
        },
    )
}

fun NavGraphBuilder.navigation(
    startDestination: NavRoute,
    route: NavRoute,
    deepLinks: List<NavDeepLink> = listOf(),
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        builder = builder,
    )
}

fun NavGraphBuilder.navigation(
    startDestination: KClass<NavRoute>,
    route: KClass<NavRoute>,
    deepLinks: List<NavDeepLink> = listOf(),
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        builder = builder,
    )
}

fun NavGraphBuilder.navigation(
    startDestination: NavRoute,
    route: KClass<NavRoute>,
    deepLinks: List<NavDeepLink> = listOf(),
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        builder = builder,
    )
}

fun NavGraphBuilder.navigation(
    startDestination: KClass<NavRoute>,
    route: NavRoute,
    deepLinks: List<NavDeepLink> = listOf(),
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination.route,
        route = route.route,
        arguments = listOf(Util.namedNavArg),
        deepLinks = deepLinks,
        builder = builder,
    )
}
