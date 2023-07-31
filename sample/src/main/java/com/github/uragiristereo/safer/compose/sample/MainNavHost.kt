package com.github.uragiristereo.safer.compose.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.github.uragiristereo.safer.compose.navigation.compose.NavHost
import com.github.uragiristereo.safer.compose.navigation.compose.composable
import com.github.uragiristereo.safer.compose.sample.screen.FeedScreen
import com.github.uragiristereo.safer.compose.sample.screen.MessagesScreen
import com.github.uragiristereo.safer.compose.sample.screen.ProfileScreen
import com.github.uragiristereo.safer.compose.sample.screen.SearchScreen
import com.github.uragiristereo.safer.compose.sample.screen.SettingsScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Feed::class,
        modifier = modifier,
    ) {
        composable(route = AppRoute.Feed) {
            FeedScreen(
                onProfileClick = onProfileClick,
                onSettingsClick = onSettingsClick,
            )
        }

        composable(route = AppRoute.Search) {
            SearchScreen()
        }

        composable(route = AppRoute.Messages) {
            MessagesScreen()
        }

        // data class without default values
        composable<AppRoute.Profile> { data ->
            // data: AppRoute.Profile?
            // the data is nullable because uses route without constructor
            data?.let {
                ProfileScreen(
                    data = it,
                    onNavigateUp = navController::navigateUp,
                )
            }
        }

        // data class with default values
        composable(route = AppRoute.Settings()) { data ->
            SettingsScreen(
                data = data,
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}
