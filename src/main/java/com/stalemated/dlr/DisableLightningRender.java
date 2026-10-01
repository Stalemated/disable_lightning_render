package com.stalemated.dlr;

import com.stalemated.dlr.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if forge {
/*import net.minecraftforge.fml.common.Mod;
*///?} elif neoforge {
/*import net.neoforged.fml.common.Mod;
*///?} elif fabric {
import net.fabricmc.api.ClientModInitializer;
//?}

//? if forge || neoforge {
/*@Mod(DisableLightningRender.MOD_ID)
*///?}
public class DisableLightningRender //? if fabric
implements ClientModInitializer
{
    public static final String MOD_ID = "disable_lightning_render";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //? if forge || neoforge {
    /*public DisableLightningRender() {
        LOGGER.info("Disable Lightning Render initialized.");
        ConfigManager.get();
    }
    *///?}

    //? if fabric {
    @Override
    public void onInitializeClient() {
        LOGGER.info("Disable Lightning Render initialized.");
        ConfigManager.get();
    }
    //?}
}
