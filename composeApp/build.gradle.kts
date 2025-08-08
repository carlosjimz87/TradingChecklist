import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=com.carlosjimz87.tradingchecklist"
        }
    }

    jvm()

    sourceSets {
        val commonMain by getting {
            resources.srcDir("src/commonMain/resources")
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.android)
                implementation(libs.koin.compose)
            }
        }

        val iosMain = maybeCreate("iosMain")
        iosMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }

        val jvmMain by getting {
            resources.srcDir("build/generated/resources/jvmMain")
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.carlosjimz87.tradingchecklist"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.carlosjimz87.tradingchecklist"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.carlosjimz87.tradingchecklist.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.carlosjimz87.tradingchecklist"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("src/jvmMain/resources/icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/jvmMain/resources/icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources/icon.png"))
            }
        }
    }
}
val i18nSourceDir = "src/commonMain/resources/i18n"

// === Copy to each target ===
val copyCommonResourcesToJvm by tasks.registering(Copy::class) {
    from(i18nSourceDir)
    into("$rootDir/composeApp/src/jvmMain/resources/i18n")
}

val copyCommonResourcesToIos by tasks.registering(Copy::class) {
    from(i18nSourceDir)
    into("$rootDir/iosApp/i18n")
}

val copyCommonResourcesToAndroid by tasks.registering(Copy::class) {
    from(i18nSourceDir)
    into("$rootDir/composeApp/src/androidMain/assets/i18n")
}

// === Hook into relevant lifecycle tasks ===
tasks.named("jvmProcessResources") {
    dependsOn(copyCommonResourcesToJvm)
}

tasks.matching { it.name.contains("ios", ignoreCase = true) && it.name.contains("Compile") }.configureEach {
    dependsOn(copyCommonResourcesToIos)
}

tasks.matching { it.name in listOf("mergeDebugAssets", "mergeReleaseAssets") }.configureEach {
    dependsOn(copyCommonResourcesToAndroid)
}