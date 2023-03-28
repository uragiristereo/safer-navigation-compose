## Guide

_Last updated: March 27th 2023_

### Table of Contents

- [Setup](#setup)
- [Quick start](#quick-start)
- [Getting data from a ViewModel](#how-about-getting-the-data-from-a-viewmodel)
- [Getting NavRoute's String route](#getting-navroutes-string-route)
- [Integrating with Bottom Navigation Bar](#integrating-with-bottom-navigation-bar)

### Setup
Refer to the [README](../README.md) page.

### Quick start

Create a sealed interface class that contains the routes.

```kotlin
sealed interface MainRoute : NavRoute {
    // object type route for route without any data
    object Home : MainRoute
    
    // data class type route with default values
    @Serializable
    data class Profile(
        val id: Int = 0,
        val fullName: String = "John Doe",
        val city: String? = null
    ) : MainRoute

    // data class route without default values
    @Serializable
    data class Post(
        val userId: Int,
        val postId: Int
    ) : MainRoute
}
```

Setup the NavHost like usual, the only differences here you're able to pass the classes that have created instead of String for the route/destination and able to retrieve the data back from the content lambda.

```kotlin
@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val mainNavController = rememberNavController()
    
    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.Home::class,
        modifier = modifier
    ) {
        // object type route for route without any data
        composable<MainRoute.Home> {
            HomeScreen()
        }

        // use constructor invocation for data class route with default values
        composable(route = MainRoute.Profile()) { data -> // data: MainRoute.Profile
            ProfileScreen(data)
        }

        // use ::class invocation for data class route without default values
        // the data from ::class invocation is nullable since it will be null when setting it as a startDestination
        composable(route = MainRoute.Post::class) { data -> // data: MainRoute.Post?
            if (data != null) {
                PostScreen(data)
            } else {
                PostScreenIfNull()
            }
        }
    }
}
```

Example navigating to the `Profile Screen`.

```kotlin
mainNavController.navigate(
    route = MainRoute.Profile(
        id = 1,
        fullName = "John Doe",
        city = "San Francisco"
    )
)
```

That's so simple isn't it?

#### How about getting the data from a `ViewModel`?

You can call an extension function for the `SavedStateHandle`, as long as the `ViewModel` is scoped with the screen.

```kotlin
class ProfileViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    // Automatically casted from the defaultValue
    val data = savedStateHandle.getData(defaultValue = MainRoute.Profile())
    
    // Or making it nullable
    val dataNullable = savedStateHandle.getData<MainRoute.Profile>()
    
    init {
        Log.d("ProfileViewModel", "data = $data")
        Log.d("ProfileViewModel", "data nullable = $dataNullable")
    }
}
```

### Getting NavRoute's String route

Getting the String route is useful for navigation logic purposes like checking which route is the current route, Bottom Navigation Bar integration, handling different transitions (for Accompanist Navigation Animation), etc.

Since the library is actually just an extension on top of the `navigation-compose` library, the actual String route from classes that extends `NavRoute` are generated automatically and can be retrieved by using one of these:

```kotlin
// for any data type route
val postRouteStr = MainRoute.Post::class.route

// for data class type route (with default values)
val profileRouteStr = MainRoute.Profile().route

// for object type route
val homeRouteStr = MainRoute.Home.route

```

### Integrating with Bottom Navigation Bar

Refer to the [:sample](../sample) project.
