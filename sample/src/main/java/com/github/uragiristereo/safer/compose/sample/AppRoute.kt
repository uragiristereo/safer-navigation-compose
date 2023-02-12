package com.github.uragiristereo.safer.compose.sample

import com.github.uragiristereo.safer.compose.navigation.core.NavRoute
import kotlinx.serialization.Serializable

sealed interface AppRoute : NavRoute {
    object Feed : AppRoute

    object Search : AppRoute

    object Messages : AppRoute

    // data class without default values
    @Serializable
    data class Profile(
        val id: Int,
        val firstName: String,
        val lastName: String,
    ) : AppRoute

    // data class with default values
    @Serializable
    data class Settings(
        val showAdvanced: Boolean = false,
        val isAdmin: Boolean = false,
    ) : AppRoute
}
