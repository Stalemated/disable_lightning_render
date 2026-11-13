package com.stalemated.dlr.config;

import com.stalemated.dlr.DisableLightningRender;
import com.stalemated.lib.compat.yacl.builder.YaclCategoryBuilder;
import com.stalemated.lib.compat.yacl.builder.YaclScreenBuilder;
import com.stalemated.lib.config.ConfigManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ConfigScreen {
    private ConfigScreen() {
    }

    public static Screen create(Screen parent) {
        return builder(parent, ConfigRegistry.CONFIG).generateScreen();
    }

    static YaclScreenBuilder builder(Screen parent, ConfigManager<DisableLightningRenderConfig> config) {
        return YaclScreenBuilder.create(parent, Component.translatable("disable_lightning_render.config.title"), DisableLightningRender.MOD_ID)
                .category(config, "general", YaclCategoryBuilder::autoGenerate);
    }
}
