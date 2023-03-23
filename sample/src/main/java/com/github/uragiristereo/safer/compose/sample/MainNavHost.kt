package com.github.uragiristereo.safer.compose.sample

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.github.uragiristereo.safer.compose.navigation.compose.NavHost
import com.github.uragiristereo.safer.compose.navigation.compose.composable
import com.github.uragiristereo.safer.compose.sample.screen.*

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
        composable<AppRoute.Feed> {
            FeedScreen(
                onProfileClick = onProfileClick,
                onSettingsClick = onSettingsClick,
            )
        }

        composable<AppRoute.Search> {
            SearchScreen()
        }

        composable<AppRoute.Messages> {
            MessagesScreen()
        }

        // data class without default values
        composable(route = AppRoute.Profile::class) { data ->
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
