## Safer Jetpack Compose Navigation Extension

Type-safe extension library for the official Jetpack Compose Navigation library with `Serializable` data classes support.

### Key features:

- Type-safe<b>*</b>, meaning it will check for failure in compile time. 
- Easy migration path, you only need to modify several lines to get started.
- Every features from the official library are still available, like nested navigation graph, deep links, etc.
- Accompanist Navigation Animation supported in a separate artifact.
- No annotation processor like **kapt** or **KSP** required<b>**</b>.
- Based on `Kotlinx.Serialization` plugin.

### Setup

Add JitPack repository to your project's `settings.gradle` file.

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven { url "https://www.jitpack.io" }
    }
}
```

Add the library and `Kotlinx.Serialization` dependencies to your project's `:app` module `build.gradle` file.

```groovy
plugins {
    // Adjust with your project's Kotlin compiler version
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

// ...

dependencies {
    def safer_navigation_ver = "1.0.0"
    
    // Core library
    implementation("com.github.uragiristereo.safer.navigation.compose:core:$safer_navigation_ver")

    // Kotlinx.Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:<version>")

    // NOTE: You only need to add ONE of these that your project currently use to avoid wrong extension import
    // Navigation Compose support
    implementation("com.github.uragiristereo.safer.navigation.compose:navigation-compose:$safer_navigation_ver")

    // Accompanist Navigation Animation support
    implementation("com.github.uragiristereo.safer.navigation.compose:navigation-animation:$safer_navigation_ver")
}
```

### Example

Create a sealed interface class that contains the routes.

```kotlin
sealed interface MainRoute : NavRoute {
    @Serializable
    object Home : NavRoute {
        override val route: String = "home"
    }
    
    @Serializable
    data class Profile(
        // It's highly recommended to specify a default value for each data class' properties
        val id: Int = 0,
        val fullName: String = "John Doe",
        val city: String? = null
    ) : NavRoute {
        // Important to add .routeWithData() after the route if it's a data class
        override val route: String = "profile".routeWithData()
    }
}
```

Setup the NavGraph like usual, the only differences here you're able to pass the classes that have created instead of String for the route/destination and able to retrieve the data back from the content lambda.

```kotlin
@Composable
fun MainNavGraph(modifier: Modifier = Modifier) {
    val mainNavController = rememberNavController()
    
    NavHost(
        startDestination = MainRoute.Home,
        modifier = modifier
    ) {
        composable(route = MainRoute.Home) {
            HomeScreen()
        }

        composable(route = MainRoute.Profile()) { data -> // data: MainRoute.Profile
            ProfileScreen(data)
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Home Screen")
    } 
}

@Composable
fun ProfileScreen(
    data: MainRoute.Profile,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Profile Screen")

        Text(text = "id =  ${data.id}")

        Text(text = "fullName =  ${data.fullName}")

        Text(text = "city =  ${data.city}")
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

You maybe want to get the data only in the `ViewModel`, you can disable deserialization in the composable builder to make sure the data doesn't get deserialized twice to optimize the performance. To do this, simply add `disableDeserialization = true` inside the `composable` parameter.

```kotlin
@Composable
fun MainNavGraph() {
    NavHost(
        // ...
    ) {
        // ...
        
        composable(
            route = MainRoute.Profile(),
            disableDeserialization = true,
        ) {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModels()
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Profile Screen")

        Text(text = "id =  ${profileViewModel.data.id}")

        Text(text = "fullName =  ${profileViewModel.data.fullName}")

        Text(text = "city =  ${profileViewModel.data.city}")
    }
}
```

### Notes
- <b>*</b> Getting the data inside a `ViewModel` is not really type-safe, since the `ViewModel` wouldn't know which screen that it's being scoped from.
- <b>**</b> Data classes that don't have default value on each parameters can be accessed without constructor using Reflection API.

### TODO
- Add a sample project.
- Add Reflection API example usage.

### License

This project is distributed under the terms of the **BSD-4-Clause** License. See the [license](LICENSE) for more information.
