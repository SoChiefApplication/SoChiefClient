plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

group = "com.vlegall"
version = "0.1.0"

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        val commonMain by getting
        val commonTest by getting

        val androidMain by getting
        val desktopMain by getting
    }
}

android {
    namespace = "com.vlegall.contracts"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Le-Gall-Valentin/sochief-client")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

