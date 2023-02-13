package com.github.uragiristereo.safer.compose.navigation.animation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import com.github.uragiristereo.safer.compose.navigation.core.route
import kotlin.reflect.KClass

@OptIn(ExperimentalAnimationApi::class)
@Composable
inline fun <reified T : NavRoute> AnimatedNavHost(
    navController: NavHostController,
    startDestination: KClass<T>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    noinline enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    noinline exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) =
        { fadeOut(animationSpec = tween(700)) },
    noinline popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) = enterTransition,
    noinline popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) = exitTransition,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    com.google.accompanist.navigation.animation.AnimatedNavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        contentAlignment = contentAlignment,
        route = route,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        builder = builder,
    )
}
