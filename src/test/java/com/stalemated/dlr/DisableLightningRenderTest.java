package com.stalemated.dlr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DisableLightningRenderTest {

    @Test
    void lightningIsHiddenUntilASettingIsFollowed() {
        assertTrue(DisableLightningRender.hidden());

        DisableLightningRender.follow(() -> false);
        assertFalse(DisableLightningRender.hidden());

        DisableLightningRender.follow(() -> true);
    }
}
