plugins {
    id("net.neoforged.moddev.legacyforge")
    id("neoforge-mutex")
}

val (mcVersion, loader) = sc.current.project.split('-')
sc.properties.tags(mcVersion, loader)

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = "${property("mod.id")}-forge"

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    else -> JavaVersion.VERSION_1_8
}

val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
    ?.asList().orEmpty().map { it.toString() }

legacyForge {
    version = "${sc.current.version}-${sc.properties.get<String>("deps.forge_version")}"

    runs {
        register("client") {
            client()
            gameDirectory.set(rootProject.file("run"))
            jvmArguments.add("-Dmixin.debug.export=true")
            programArguments.addAll("--mixin.config", "disable_lightning_render.mixins.json")
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

        val forgeCompat = sc.properties.getOrNull<String>("deps.forge_compat") ?: sc.properties.get<String>("mod.mc_compat")
        val forgeMin = sc.properties.getOrNull<String>("deps.forge_min") ?: "47"
        val props = buildMap {
            register("id", sc.properties["mod.id"])
            register("name", sc.properties["mod.name"])
            register("version", sc.properties["mod.version"])
            register("minecraft", forgeCompat)
            register("forge_min", forgeMin)
        }

        filesMatching("META-INF/mods.toml") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }

        exclude("fabric.mod.json", "META-INF/neoforge.mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    withType<Jar> {
        val name = project.property("mod.id")
        inputs.property("mod_id", name)
        manifest {
            attributes("MixinConfigs" to "disable_lightning_render.mixins.json")
        }
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
