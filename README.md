Manoj Spoofer

<p align="center">
  <b>Android Location Spoofing Module</b><br>
  A location simulation project built for Android devices using Java and LSPosed.
</p>---

About the Project

Manoj Spoofer is an Android location simulation application and Xposed module designed to provide a configurable location environment on supported Android devices.

The project combines a native Android interface with an interactive map to allow users to select geographic coordinates and manage location settings.

It is developed using Java, Android SDK, and the Xposed API, with a lightweight web-based map interface.

This repository contains the source code and project configuration for developers who want to study, build, or extend the application.

Features

- Interactive map interface.
- Coordinate selection.
- Location configuration.
- Android application interface.
- LSPosed module integration.
- Java-based implementation.
- Lightweight HTML and JavaScript components.
- Root-enabled environment support.
- Customizable source code.
- Android Studio and Gradle project structure.

Screenshots

Screenshots will be added in a future update.

Technology Stack

Component| Technology
Programming language| Java
Platform| Android
Build system| Gradle
Android Gradle Plugin| 8.1.1
Compile SDK| 34
Java compatibility| Java 8
Module framework| Xposed API
Map interface| HTML, CSS, JavaScript
Map library| Leaflet

Requirements

Before building or using the project, ensure that the following components are available:

- Android device running a compatible Android version.
- Root access where required.
- Magisk or another compatible root solution.
- LSPosed Framework.
- Android SDK.
- Java Development Kit (JDK).
- Gradle-compatible build environment.

Note: Compatibility depends on the Android version, installed framework, and target application.

Project Structure

manoj-spoofer/
│
├── app/
│   ├── build.gradle
│   └── src/
│       └── main/
│           ├── java/
│           ├── res/
│           ├── assets/
│           └── AndroidManifest.xml
│
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
└── .gitignore

Building from Source

1. Clone the repository

git clone https://github.com/Manojgowdapv/manojgowda-spoofer.git

2. Enter the project directory

cd manojgowda-spoofer

3. Build the project

If a compatible Gradle installation is available:

gradle assembleDebug

The generated APK will normally be located in:

app/build/outputs/apk/debug/

4. Install the APK

Install the generated APK on a compatible test device and configure the required framework and permissions.

Configuration

The project includes configuration files for the Android build system and module integration.

The supplied source archive may contain environment-specific settings. Review the Gradle configuration before building on a different machine.

In particular, check for any custom AAPT2 path in "gradle.properties" and update or remove it if the path is unavailable in your environment.

Troubleshooting

Build fails with an SDK error

Verify that the Android SDK and required platform are installed.

Gradle dependency errors

Check your internet connection and ensure that the configured repositories are accessible.

AAPT2 executable not found

Review the AAPT2 override in "gradle.properties".

Module not detected

Verify that the module configuration and entry-point files are included in the APK.

Application does not behave as expected

Check Android permissions, framework compatibility, and application logs.

Development

Contributions, bug reports, and suggestions are welcome.

When modifying the project:

1. Keep the original source files backed up.
2. Test changes on a dedicated device.
3. Verify compatibility before publishing a new release.
4. Document significant changes.

Disclaimer

This project is provided for educational, development, and authorized testing purposes.

Users are responsible for complying with applicable laws, platform policies, and the terms of service of any applications they use.

The developer is not responsible for misuse, account restrictions, or damages resulting from unauthorized use.

License

No license has been specified yet. All rights remain with the project owner unless a license is added to this repository.

Author

Manoj Gowda

GitHub: "@Manojgowdapv" (https://github.com/Manojgowdapv)

Project: "Manoj Spoofer" (https://github.com/Manojgowdapv/manojgowda-spoofer)

---

<p align="center">
  Made with ❤️ by Manoj Gowda
</p>
