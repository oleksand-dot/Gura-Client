package com.guraclient.mixin;

import com.guraclient.config.GuraConfig;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NOTE: the exact method name/signature for crosshair rendering can shift
 * slightly between Yarn mapping builds. If this fails to compile once
 * Gradle syncs, open InGameHud in your IDE (Cursor will resolve it via the
 * attached Minecraft sources) and update the target below to match —
 * look for the method that draws the crosshair, usually called from
 * InGameHud#render.
 */
@Mixin(InGameHud.class)
public class CrosshairMixin {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void gura$cancelVanillaCrosshair(CallbackInfo ci) {
        if (GuraConfig.INSTANCE.crosshairEnabled) {
            ci.cancel();
        }
    }
}
