plugins {
    id("net.neoforged.moddev")
    id("neoforge-mutex")
}

val (mcVersion, loader) = sc.current.project.split('-')
sc.properties.tags(mcVersion, loader)

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = "${property("mod.id")}-neoforge"

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    else -> JavaVersion.VERSION_1_8
}

val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
    ?.asList().orEmpty().map { it.toString() }

neoForge {
    version = sc.properties.get<String>("deps.neoforge_version")

    runs {
        register("client") {
            client()
            gameDirectory.set(rootProject.file("run"))
            jvmArguments.add("-Dmixin.debug.export=true")
            disableIdeRun()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets.main.get())
        }
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }

        val nfCompat = sc.properties.getOrNull<String>("deps.neoforge_compat") ?: "[2,)"
        val props = buildMap {
            register("id", sc.properties["mod.id"])
            register("name", sc.properties["mod.name"])
            register("version", sc.properties["mod.version"])
            register("minecraft", sc.properties["mod.mc_compat"])
            register("neoforge_compat", nfCompat)
        }

        filesMatching("META-INF/neoforge.mods.toml") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }

        exclude("fabric.mod.json", "META-INF/mods.toml", "pack.mcmeta")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    withType<Jar> {
        val name = project.property("mod.id")
        inputs.property("mod_id", name)
        val licenseFile = rootProject.file("LICENSE")
        if (licenseFile.exists()) {
            from(licenseFile) { rename { "$it-$name" } }
        }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        from(named<Jar>("jar"))
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }
}
