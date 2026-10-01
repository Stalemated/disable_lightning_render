plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "1.20.1-fabric"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    swaps["mod_version"] = "\"${property("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
    constants["release"] = property("mod.id") != "template"

    val loader = node.project.name.substringAfter('-')
    constants["fabric"] = loader == "fabric"
    constants["forge"] = loader == "forge"
    constants["neoforge"] = loader == "neoforge"

    replacements {
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }

        string(current.parsed >= "26.1") {
            replace("classTweaker v2 named", "classTweaker v2 official")
        }
    }
}
