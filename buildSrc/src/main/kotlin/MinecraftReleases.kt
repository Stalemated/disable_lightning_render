import dev.kikugie.stonecutter.build.StonecutterBuildExtension

fun StonecutterBuildExtension.minecraftReleases(): List<String> {
    val releases = properties.rawOrNull("mod", "mc_releases")?.asList()?.map { it.toString() }.orEmpty()
    require(releases.isNotEmpty()) { "mod.mc_releases is missing or empty for node ${current.project}" }
    return releases
}

fun StonecutterBuildExtension.minecraftRange(): String {
    val releases = minecraftReleases()
    return if (releases.size == 1) releases.single() else "${releases.first()}-${releases.last()}"
}
