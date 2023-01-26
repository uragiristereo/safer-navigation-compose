package com.github.uragiristereo.safer.compose.navigation.core

import android.util.Log
import androidx.navigation.NavBackStackEntry

object NavRouteUtil {
    inline fun <reified T> getDataOrNull(
        route: NavRoute,
        entry: NavBackStackEntry,
    ): T? {
        return when (val data = entry.arguments?.getString("data")) {
            null -> {
                val e = IllegalArgumentException("Expecting navigation route data for \"${route.route}\" but got null!")

                Log.w("SaferComposeNavigation", e)

                null
            }

            else -> Serializer.decode(data)
        }
    }
}
