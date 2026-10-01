import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

// Prevents NeoFormRuntime / NFRT from overloading system resources by compiling Minecraft on multiple versions in parallel
interface NeoForgeMutex : BuildService<BuildServiceParameters.None>

val mutex = gradle.sharedServices.registerIfAbsent("createMinecraftArtifactsMutex", NeoForgeMutex::class.java) {
    maxParallelUsages.set(1)
}

tasks.named { it == "createMinecraftArtifacts" }.configureEach {
    usesService(mutex)
}
