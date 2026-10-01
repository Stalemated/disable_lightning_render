pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    // Check the latest version on https://stonecutter.kikugie.dev/blog/changes/0.9
    id("dev.kikugie.stonecutter") version "0.9.8"

    // Used for cross-compat for 26.1+ and older versions (https://codeberg.org/KikuGie/loom-back-compat)
    id("dev.kikugie.loom-back-compat") version "0.4.2"

    // NeoForged ModDevGradle for NeoForge & Legacy Forge
    id("net.neoforged.moddev") version "2.0.147" apply false
    id("net.neoforged.moddev.legacyforge") version "2.0.147" apply false

    // (https://github.com/gradle/foojay-toolchains)
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        mapBuilds { _, node ->
            val loader = node.project.substringAfter('-')
            "build-$loader.gradle.kts"
        }

        versions(
            // 1.20 - 1.20.1: Fabric, Forge
            "1.20.1-fabric" to "1.20.1",
            "1.20.1-forge" to "1.20.1",

            // 1.20.2 - 1.20.4: Fabric
            "1.20.4-fabric" to "1.20.4",

            // 1.20.5 - 1.20.6: Fabric
            "1.20.6-fabric" to "1.20.6",

            // 1.21 - 1.21.1: Fabric, NeoForge
            "1.21.1-fabric" to "1.21.1",
            "1.21.1-neoforge" to "1.21.1",

            // 1.21.2 - 1.21.3: Fabric, NeoForge
            "1.21.3-fabric" to "1.21.3",
            "1.21.3-neoforge" to "1.21.3",

            // 1.21.4 - 1.21.11: Fabric, NeoForge
            "1.21.11-fabric" to "1.21.11",
            "1.21.11-neoforge" to "1.21.11",

            // 26.1 - 26.3: Fabric, NeoForge
            "26.3-fabric" to "26.3",
            "26.3-neoforge" to "26.3"
        )
        vcsVersion = "1.20.1-fabric"
    }
}

rootProject.name = "disable_lightning_render"
