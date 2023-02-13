package com.github.uragiristereo.safer.compose.sample.bottomnavigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.uragiristereo.safer.compose.navigation.core.route
import com.github.uragiristereo.safer.compose.sample.AppRoute

enum class BottomNavigationBarItems(
    val route: AppRoute,
    val label: String,
    val icon: ImageVector,
) {
    Feed(
        route = AppRoute.Feed,
        label = "Feed",
        icon = Icons.Default.Home,
    ),
    Search(
        route = AppRoute.Search,
        label = "Search",
        icon = Icons.Default.Search,
    ),
    Messages(
        route = AppRoute.Messages,
        label = "Messages",
        icon = Icons.Default.Email,
    ),
}

fun Array<BottomNavigationBarItems>.toRouteStringList(): List<String> {
    return map { it.route::class.route }
}
