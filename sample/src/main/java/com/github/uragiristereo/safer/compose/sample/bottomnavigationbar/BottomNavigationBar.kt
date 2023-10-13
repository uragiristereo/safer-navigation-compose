package com.github.uragiristereo.safer.compose.sample.bottomnavigationbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.uragiristereo.safer.compose.navigation.core.route
import com.github.uragiristereo.safer.compose.sample.AppRoute

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onItemClick: (AppRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = currentRoute in BottomNavigationBarItems.entries.toTypedArray().toRouteStringList(),
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(modifier = modifier) {
            BottomNavigationBarItems.entries.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route::class.route,
                    label = {
                        Text(text = item.label)
                    },
                    onClick = {
                        onItemClick(item.route)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                        )
                    },
                )
            }
        }
    }
}
