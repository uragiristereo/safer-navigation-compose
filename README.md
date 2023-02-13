## Safer Jetpack Compose Navigation Extension

Type-safe extension library for the official Jetpack Compose Navigation library with `Serializable` data classes support.

This library is trying to solve many of the `androidx.navigation:navigation-compose` flaws, i.e. you can't send a class when navigating, no type-safety and boilerplate setup.

Under the hood, the library uses base64 encoded JSON serialization to send and get the data back. But you don't need to do it manually as the library handles it automatically.

### Features

- Type-safe, meaning it will check for failure in compile time. 
- Easy migration path, you only need to modify a small amount of code in an existing codebase to get started.
- Every features from the official library are still available, like nested navigation graph, deep links, etc.
- Accompanist Navigation Animation supported in a separate artifact.
- No annotation processor like **kapt** or **KSP** required.
- Based on `Kotlinx.Serialization` plugin.

### Setup

Add JitPack repository to your project's `settings.gradle` file.

```groovy
dependencyResolutionManagement {
    // ...

    repositories {
        // ...
        maven { url "https://www.jitpack.io" }
    }
}
```

Add `Kotlinx.Serialization` plugin to your project's `build.gradle` file.

```groovy
plugins {
    // Adjust with your project's Kotlin compiler version
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21" apply false
}
```

Add the library and `Kotlinx.Serialization` dependencies to your project's `:app` module `build.gradle` file.

```groovy
plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

// ...

dependencies {
    // Kotlinx.Serialization
    def kotlinx_serialization_version = "1.4.1"
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")
    
    // Core library
    def safer_navigation_version = "1.1.0"
    implementation("com.github.uragiristereo.safer.navigation.compose:core:$safer_navigation_version")

    // NOTE: You only need to add ONE of these that your project currently use to avoid wrong extension to import
    
    // Navigation Compose support
    def navigation_version = "2.5.3"
    implementation("androidx.navigation:navigation-compose:$navigation_version")
    implementation("com.github.uragiristereo.safer.navigation.compose:navigation-compose:$safer_navigation_version")

    // Or, Accompanist Navigation Animation support
    def accompanist_version = "0.28.0"
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanist_version")
    implementation("com.github.uragiristereo.safer.navigation.compose:navigation-animation:$safer_navigation_version")
}
```

### Usage

Refer to the [quick start guide](/docs/GUIDE.md) page.

### Available extension functions
- Core
    - `NavGraphBuilder.dialog()`
    - `NavHostController.navigate()`
    - `NavOptionsBuilder.popUpTo()`
    - `SavedStateHandle.getData()`
- Navigation Compose
    - `NavHost()`
    - `NavGraphBuilder.composable()`
    - `NavGraphBuilder.navigation()`
- Accompanist Navigation Animation
    - `AnimatedNavHost()`
    - `NavGraphBuilder.composable()`
    - `NavGraphBuilder.navigation()`

### License

This project is distributed under the terms of the **BSD-4-Clause** License. See the [license](LICENSE) for more information.
