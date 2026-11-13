package com.stalemated.dlr.config;

import com.stalemated.dlr.DisableLightningRender;
import com.stalemated.lib.client.SLibConfigScreens;
import com.stalemated.lib.config.LocalConfigManager;
import com.stalemated.lib.config.SLibConfig;

public final class ConfigRegistry {
    static final LocalConfigManager<DisableLightningRenderConfig> CONFIG = SLibConfig.local(DisableLightningRender.MOD_ID, DisableLightningRenderConfig.class).build();

    private ConfigRegistry() {
    }

    public static void register() {
        CONFIG.register();
        DisableLightningRender.follow(() -> CONFIG.get().hideLightning);
        SLibConfigScreens.register(DisableLightningRender.MOD_ID, () -> ConfigScreen::create);
    }
}
