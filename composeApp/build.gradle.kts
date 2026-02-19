import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val versionNameProp: String? = providers.gradleProperty("VERSION_NAME").get()
val versionCodeProp: Int = providers.gradleProperty("VERSION_CODE").get().toInt()
val desktopPackageVersionProp: String? = providers.gradleProperty("DESKTOP_PACKAGE_VERSION").get()

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

compose {
    resources {
        publicResClass = true
    }
}

kotlin {
    targets.all {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    androidTarget {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }

    jvm {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.security.crypto)

            implementation(libs.ktor.client.okhttp)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.compose.uiTooling)
            implementation(libs.compose.uiToolingPreview)
        }
        commonMain.dependencies {
            implementation(libs.contracts)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.cio)

            implementation(libs.icons.lucide.cmp)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation(libs.ktor.client.cio)
        }
    }
}

android {
    namespace = "fr.vlegall.sochief.client"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "fr.vlegall.client"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeProp
        versionName = versionNameProp
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].java.srcDirs("src/androidMain/kotlin")

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") { isMinifyEnabled = false }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    signingConfigs {
        create("release") {
            val ksPath = System.getenv("ANDROID_KEYSTORE_PATH")
                ?: (findProperty("ANDROID_KEYSTORE_PATH") as String?)
            val ksPass = System.getenv("ANDROID_KEYSTORE_PASSWORD")
                ?: (findProperty("ANDROID_KEYSTORE_PASSWORD") as String?)
            val keyAlias = System.getenv("ANDROID_KEY_ALIAS")
                ?: (findProperty("ANDROID_KEY_ALIAS") as String?)
            val keyPass = System.getenv("ANDROID_KEY_PASSWORD")
                ?: (findProperty("ANDROID_KEY_PASSWORD") as String?)

            if (!ksPath.isNullOrBlank()) storeFile = file(ksPath)
            if (!ksPass.isNullOrBlank()) storePassword = ksPass
            if (!keyAlias.isNullOrBlank()) this.keyAlias = keyAlias
            if (!keyPass.isNullOrBlank()) keyPassword = keyPass

            enableV1Signing = true
            enableV2Signing = true
        }
    }

    val hasKeystore = !(
            (System.getenv("ANDROID_KEYSTORE_PATH") ?: (findProperty("ANDROID_KEYSTORE_PATH") as String?))
            ).isNullOrBlank()

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            if (hasKeystore) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}


dependencies {
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "fr.vlegall.sochief.client.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SoChief"
            packageVersion = desktopPackageVersionProp

            macOS {
                iconFile.set(rootProject.file("icons/app.icns"))
                packageVersion = desktopPackageVersionProp
                dmgPackageVersion = desktopPackageVersionProp
            }

            linux {
                iconFile.set(rootProject.file("icons/app.png"))
            }

            windows {
                iconFile.set(rootProject.file("icons/app.ico"))
                shortcut = true          // crée raccourci
                menuGroup = "SoChief"    // dossier menu démarrer
            }
        }
    }
}
