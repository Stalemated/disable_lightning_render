package com.stalemated.dlr.config;

import com.stalemated.lib.compat.yacl.SLibTesting;
import com.stalemated.lib.config.InMemoryConfigManager;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class DisableLightningRenderConfigScreenTest {

    @Test
    void theConfigScreenBuilds() throws Exception {
        SLibTesting.assertScreenBuilds(
                ConfigScreen.builder(null, new InMemoryConfigManager<>(new DisableLightningRenderConfig(), DisableLightningRenderConfig::new, null)),
                Path.of(DisableLightningRenderConfigScreenTest.class.getResource("/assets/disable_lightning_render/lang/en_us.json").toURI())
        );
    }
}
