# Kotlin Multiplatform Checklist App

This is a **Kotlin Multiplatform** project targeting:

- ✅ **Android**
- ✅ **iOS**
- ✅ **Desktop (JVM)**

The project is designed to demonstrate a shared UI and logic using **JetBrains Compose Multiplatform**, with proper state management, UI animations, and platform-specific storage support.

---

## 🧩 Project Structure

### `/composeApp`
Shared code and UI for all platforms.

- `commonMain`: Logic and UI shared across Android, iOS, and Desktop.
- `androidMain`: Android-specific code (e.g., context usage, local storage).
- `iosMain`: iOS-specific code (e.g., NSFileManager, Apple APIs).
- `jvmMain`: Desktop-specific code using Java APIs for file handling.

### `/iosApp`
Native iOS entry point.

- SwiftUI wrapper for launching the shared Compose UI.
- Xcode project to run on iPhone simulators or real devices.

---

## ✨ Features

- 🧾 Checklist view with items, progress tracking, and reset option
- 📊 Linear and Circular progress indicators depending on screen size
- ✅ Smooth entry animations and UI transitions
- 🔁 Snackbar with customizable style and position
- 💾 Persistent local storage per platform (JSON via Kotlinx.serialization)
- 🧠 Multi-platform state management using Kotlin’s state & coroutine APIs
- 🧪 Prepared for unit and UI testing on all targets

---

## 🚀 Getting Started

### Android
```bash
./gradlew :androidApp:installDebug
```

### Desktop (JVM)
```bash
./gradlew :desktopApp:run
```

### iOS
- Open `/iosApp/iosApp.xcodeproj` in Xcode
- Select a simulator or device and press **Run**

> Note: You must have Xcode and Kotlin Multiplatform Mobile plugins installed.

---

## 🛠️ Tech Stack

- Kotlin Multiplatform
- Jetpack Compose Multiplatform (UI)
- Kotlinx Serialization (JSON)
- Koin (Dependency Injection)
- SwiftUI (entry point wrapper)
- NSFileManager, File API (persistence)
- Gradle Multiplatform Configuration

---

## 📂 Architecture

```
+ shared/
|-- domain/
|-- data/
|-- ui/
+ platform/
|-- android/
|-- ios/
|-- desktop/
```

Modular and platform-specific logic decoupled with `expect/actual` mechanism.

---

## 📚 Resources

- [JetBrains Compose Multiplatform Docs](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Kotlin Multiplatform Docs](https://www.jetbrains.com/help/kotlin-multiplatform-dev/)
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Koin DI](https://insert-koin.io/)

---

## 🧪 To Do

- [ ] Add UI tests
- [ ] Implement real cloud sync support
- [ ] Enable push notifications on mobile
- [ ] Support multiple checklists

---

## 📜 License

MIT © YourNameHere

---

Made with ❤️ using Kotlin Multiplatform.