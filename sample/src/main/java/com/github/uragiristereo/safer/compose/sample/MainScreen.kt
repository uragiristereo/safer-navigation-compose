package com.github.uragiristereo.safer.compose.sample

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.uragiristereo.safer.compose.navigation.core.navigate
import com.github.uragiristereo.safer.compose.sample.bottomnavigationbar.BottomNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(id = navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        },
        modifier = modifier,
    ) {
        MainNavHost(
            navController = navController,
            onProfileClick = {
                navController.navigate(
                    route = AppRoute.Profile(
                        id = 1000,
                        firstName = "John",
                        lastName = "Doe",
                    )
                )
            },
            onSettingsClick = {
                navController.navigate(
                    route = AppRoute.Settings(
                        showAdvanced = true,
                    )
                )
            },
        )
    }
}
