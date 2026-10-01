package com.stalemated.dlr.mixin;

import com.stalemated.dlr.config.ConfigManager;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {

    @Inject(method = "shouldRenderAtSqrDistance", at = @At("HEAD"), cancellable = true)
    private void disableLightningRender(double distance, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigManager.get().disableLightningRender) {
            cir.setReturnValue(false);
        }
    }
}
