plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.compose") version "1.7.1"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
}

group = "com.example"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jogamp.org/deployment/maven/")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.ui)

    // JavaFX dependencies direct from Maven Central for Linux
    val jfxVersion = "21"
    implementation("org.openjfx:javafx-base:$jfxVersion:linux")
    implementation("org.openjfx:javafx-graphics:$jfxVersion:linux")
    implementation("org.openjfx:javafx-controls:$jfxVersion:linux")
    implementation("org.openjfx:javafx-media:$jfxVersion:linux")
    implementation("org.openjfx:javafx-web:$jfxVersion:linux")
    implementation("org.openjfx:javafx-swing:$jfxVersion:linux")

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // KCEF for Chromium WebView support
    implementation("io.github.kevinnzou:compose-webview-multiplatform:1.9.40")

    // VLCJ for robust media playback using native VLC
    implementation("uk.co.caprica:vlcj:4.8.2")
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Pkg
            )
            packageName = "youcloud"
            packageVersion = "1.0.0"
        }
    }
}
