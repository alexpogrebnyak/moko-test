/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.detekt) apply false
}

allprojects {
    repositories {
        google()
        jcenter()

        maven { url = uri("https://kotlin.bintray.com/kotlin") }
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
    }

    apply(plugin = Deps.Plugins.detekt.id)

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        input.setFrom("src/commonMain/kotlin", "src/androidMain/kotlin", "src/iosMain/kotlin")
    }

    dependencies {
        "detektPlugins"(Deps.Libs.Jvm.detektFormatting)
    }

    plugins.withId(Deps.Plugins.androidLibrary.id) {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdkVersion(Deps.Android.compileSdk)

            defaultConfig {
                minSdkVersion(Deps.Android.minSdk)
                targetSdkVersion(Deps.Android.targetSdk)
            }
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
