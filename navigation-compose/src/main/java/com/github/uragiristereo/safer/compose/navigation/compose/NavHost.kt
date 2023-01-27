package com.github.uragiristereo.safer.compose.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import kotlin.reflect.KClass

@Composable
inline fun <reified T : NavRoute> NavHost(
    navController: NavHostController,
    startDestination: T,
    modifier: Modifier = Modifier,
    route: String? = null,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        route = route,
        builder = builder,
    )
}

@Composable
inline fun <reified T : NavRoute> NavHost(
    navController: NavHostController,
    startDestination: KClass<T>,
    modifier: Modifier = Modifier,
    route: String? = null,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    val startRoute = startDestination.java.getConstructor().newInstance()

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startRoute.route,
        modifier = modifier,
        route = route,
        builder = builder,
    )
}
