# TimeTrackerSDK

Lightweight Kotlin SDK to measure execution time of operations in Android apps — suitable for both debugging and production use.

---

## Installation

### Using [JitPack](https://jitpack.io/#yourusername/TimeTrackerSDK)

1. **Add JitPack to your root `build.gradle(.kts)`**:

```kotlin
repositories {
    maven("https://jitpack.io")
}
```

2. **Add the dependency in your `app/build.gradle(.kts)`**:

```kotlin
dependencies {
    implementation("com.github.yourusername:TimeTrackerSDK:v1.0.0")
}
```

---

## Usage

### Enable Tracking (usually in `onCreate()`)

```kotlin
TimeTracker.isEnabled = BuildConfig.DEBUG
TimeTracker.logger = { tag, msg -> Log.d(tag, msg) }
```

### Manual start/stop

```kotlin
TimeTracker.start("load_user")
// ... some logic
TimeTracker.stop("load_user")

val duration = TimeTracker.getDuration("load_user")
Log.d("Time", "load_user took $duration ms")
```

### Automatic block timing

```kotlin
TimeTracker.measure("fetch_settings") {
    fetchRemoteSettings()
}
```

### Print all recorded durations

```kotlin
TimeTracker.printAllDurations()
```

### Reset tracking

```kotlin
TimeTracker.reset()
```

---

## Jetpack Compose Example

```kotlin
@Composable
fun Greeting(name: String) {
    LaunchedEffect(Unit) {
        TimeTracker.measure("greeting_draw") {
            delay(100) // simulate load
        }
    }

    Text("Hello $name!")
}
```

---

## Where to use TimeTracker

You can use `TimeTracker` in any layer of your app:

- **Activities & Fragments** (`onCreate`, `onResume`, etc.)
- **ViewModel** (for business logic, API/data layer timing)
- **Repositories** and Use Cases
- Inside **Jetpack Compose** via `LaunchedEffect`, `SideEffect`
- Avoid direct use inside `@Composable` without effect scopes

---

## ViewModel Example

```kotlin
class MyViewModel : ViewModel() {
    fun loadData() {
        TimeTracker.measure("loadDataInVM") {
            // simulate loading
            Thread.sleep(150)
        }
    }
}
```

---

## Repository Example

```kotlin
class UserRepository {
    fun fetchUser(): User {
        TimeTracker.start("user_fetch")
        val user = api.getUser()
        TimeTracker.stop("user_fetch")
        return user
    }
}
```

---

## Production-ready
- Safe to disable in release builds: `TimeTracker.isEnabled = BuildConfig.DEBUG`
- Custom logger support (e.g. Crashlytics, Timber)
- No runtime dependencies

---

## 📃 License

[MIT](LICENSE)
