package com.github.uragiristereo.safer.compose.navigation.core

import android.util.Log
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlin.reflect.KClass

object Util {
    const val DATA = "data"
    const val DATA_BRACKETS = "{data}"
    const val DATA_FULL = "?data={data}"

    inline fun <reified T : NavRoute> getDataOrNull(
        route: KClass<T>,
        entry: NavBackStackEntry,
    ): T? {
        val data = entry.arguments?.getString(DATA)

        return when {
            isClassAnObject(route) -> getObjectInstance(route)

            data == null -> {
                val e = IllegalArgumentException("Expecting navigation route data for \"${route.route}\" but got null!")

                Log.d("SaferNavigationCompose", "${e.message}")

                null
            }

            else -> Serializer.decode(data)
        }
    }

    inline fun <reified T : Any> isClassAnObject(klass: KClass<T>): Boolean {
        return klass.java.declaredFields.any {
            it.type == klass.java && it.name == "INSTANCE"
        }
    }

    inline fun <reified T : Any> getObjectInstance(klass: KClass<T>): T {
        return klass.java.getDeclaredField("INSTANCE").get(null) as T
    }

    val namedNavArg = navArgument(name = DATA) {
        type = NavType.StringType
        nullable = true
        defaultValue = null
    }
}
