package com.stalemated.dlr;

import com.stalemated.dlr.config.ConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

//? if fabric {
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
//?} elif forge {
/*import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
*///?} else {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
*///?}

//? if forge || neoforge {
/*@Mod(DisableLightningRender.MOD_ID)
*///?}
public final class DisableLightningRender //? if fabric
implements ClientModInitializer
{
    public static final String MOD_ID = "disable_lightning_render";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final String SLIB_ID = "s_lib";
    private static final String SLIB_MINIMUM_VERSION = "4.0.0";
    private static volatile BooleanSupplier hidden = () -> true;

    //? if fabric {
    @Override
    public void onInitializeClient() {
        if (slibUsable()) ConfigRegistry.register();
    }
    //?} elif forge {
    /*public DisableLightningRender() {
        if (FMLEnvironment.dist == Dist.CLIENT && slibUsable()) ConfigRegistry.register();
    }
    *///?} else {
    /*public DisableLightningRender(Dist dist) {
        if (dist == Dist.CLIENT && slibUsable()) ConfigRegistry.register();
    }
    *///?}

    public static boolean hidden() {
        return hidden.getAsBoolean();
    }

    public static void follow(BooleanSupplier source) {
        hidden = Objects.requireNonNull(source, "source cannot be null");
    }

    //? if fabric {
    private static boolean slibUsable() {
        Optional<Version> installed = FabricLoader.getInstance().getModContainer(SLIB_ID).map(mod -> mod.getMetadata().getVersion());
        if (installed.isEmpty()) return false;
        if (installed.get().compareTo(slibMinimumVersion()) >= 0) return true;
        return outdatedSlib(installed.get().getFriendlyString());
    }

    private static Version slibMinimumVersion() {
        try {
            return Version.parse(SLIB_MINIMUM_VERSION);
        } catch (VersionParsingException e) {
            throw new IllegalStateException("Cannot parse the minimum S-Lib version " + SLIB_MINIMUM_VERSION, e);
        }
    }
    //?} else {
    /*private static boolean slibUsable() {
        Optional<ArtifactVersion> installed = ModList.get().getModContainerById(SLIB_ID).map(mod -> mod.getModInfo().getVersion());
        if (installed.isEmpty()) return false;
        if (installed.get().compareTo(new DefaultArtifactVersion(SLIB_MINIMUM_VERSION)) >= 0) return true;
        return outdatedSlib(installed.get().toString());
    }
    *///?}

    private static boolean outdatedSlib(String installed) {
        LOGGER.info("S-Lib {} is older than {}, so Disable Lightning Render always hides lightning", installed, SLIB_MINIMUM_VERSION);
        return false;
    }
}
