# Manoj Spoofer

Android location-spoofing application/module source project.

## Project contents

- `app/src/main/java/` — Java source
- `app/src/main/AndroidManifest.xml` — Android manifest
- `app/src/main/res/` — app resources and layouts
- `app/src/main/assets/xposed_init` — Xposed module entry point
- `app/build.gradle` — Android app build configuration
- `settings.gradle` — project settings and dependency repositories

## Build notes

This archive preserves the source and project configuration supplied by the project owner. It excludes generated build output and local Gradle caches.

The supplied `settings.gradle` includes a Termux-specific AAPT2 override. For Android Studio or another environment, remove or update the `android.aapt2FromMavenOverride` setting in `gradle.properties` if it points to a path that does not exist on that device.

The project uses Android Gradle Plugin 8.1.1, compile SDK 34, Java 8 compatibility, and the Xposed API as a compile-only dependency. A compatible Gradle installation/wrapper and Android SDK must be available to build it.
