package com.github.uragiristereo.safer.compose.navigation.core

import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

object SncUtil {
    private val navRoutes = mutableMapOf<String, PolymorphicModuleBuilder<NavRoute>.() -> Unit>()
    private var module: SerializersModule? = null

    val json: Json
        get() = Json {
            ignoreUnknownKeys = true
            module?.let { serializersModule = it }
        }

    inline fun <reified T : NavRoute> registerRoute(klass: KClass<T>) {
        if (!isClassAnObject(klass)) {
            addPolymorphicType(name = klass.qualifiedName!!) {
                subclass(klass, serializer())
            }
        }
    }

    fun addPolymorphicType(
        name: String,
        builder: PolymorphicModuleBuilder<NavRoute>.() -> Unit,
    ) {
        navRoutes[name] = (builder)

        updateModule()
    }

    private fun updateModule() {
        module = SerializersModule {
            polymorphic(NavRoute::class) {
                navRoutes.forEach { (_, builder) ->
                    builder()
                }
            }
        }
    }

    inline fun <reified T> encode(value: T): String {
        val data = json.encodeToString(value)

        return Uri.encode(Base64.encodeToString(data.toByteArray(), Base64.DEFAULT))
    }

    inline fun <reified T> decode(value: String): T? {
        return try {
            val decoded = Base64
                .decode(value, Base64.DEFAULT)
                .toString(charset("UTF-8"))

            json.decodeFromString(decoded)
        } catch (e: IllegalArgumentException) {
            if (SaferNavigationComposeConfig.isLoggingEnabled) {
                Log.w("SaferComposeNavigation", e)
            }

            null
        }
    }

    inline fun <reified T : NavRoute> getDataOrNull(
        route: KClass<T>,
        entry: NavBackStackEntry,
    ): T? {
        val data = entry.arguments?.getString("snc-data")

        return when {
            isClassAnObject(route) -> getObjectInstance(route)

            data == null -> {
                val e = IllegalArgumentException("Expecting navigation route data for \"${route.route}\" but got null!")

                if (SaferNavigationComposeConfig.isLoggingEnabled) {
                    Log.d("SaferNavigationCompose", "${e.message}")
                }

                null
            }

            else -> decode(data)
        }
    }

    fun isClassAnObject(klass: KClass<out NavRoute>): Boolean {
        return klass.java.declaredFields.any {
            it.type == klass.java && it.name == "INSTANCE"
        }
    }

    inline fun <reified T : Any> getObjectInstance(klass: KClass<T>): T {
        return klass.java.getDeclaredField("INSTANCE").get(null) as T
    }

    val namedNavArg = navArgument(name = "snc-data") {
        type = NavType.StringType
        nullable = true
        defaultValue = null
    }
}
