package com.guraclient.hud;

import com.guraclient.config.GuraConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

/**
 * Draws letterbox/pillarbox bars to visually lock the game to a target
 * aspect ratio, without touching the actual window/framebuffer resolution
 * (safer and much simpler than remapping the projection matrix, and works
 * fine on phones running through Fold Craft Launcher where the real
 * window ratio is whatever the device screen is).
 *
 * Draw this FIRST in your HUD render chain, before HudRenderer and
 * CrosshairRenderer, so bars sit behind everything else... actually since
 * these are opaque bars over the game world, draw them LAST (on top) but
 * make sure HudRenderer/CrosshairRenderer already account for the safe
 * area if you want elements to never sit under a bar.
 */
public class AspectRatioRenderer {

    public static void render(DrawContext context, MinecraftClient mc) {
        GuraConfig cfg = GuraConfig.INSTANCE;
        if (!cfg.aspectRatioLockEnabled) return;

        int screenW = context.getScaledWindowWidth();
        int screenH = context.getScaledWindowHeight();
        float currentRatio = (float) screenW / screenH;
        float target = cfg.targetAspectRatio;

        int barColor = 0xFF000000;

        if (currentRatio > target) {
            // screen is wider than target -> pillarbox (bars on left/right)
            int idealWidth = Math.round(screenH * target);
            int barWidth = (screenW - idealWidth) / 2;
            if (barWidth > 0) {
                context.fill(0, 0, barWidth, screenH, barColor);
                context.fill(screenW - barWidth, 0, screenW, screenH, barColor);
            }
        } else if (currentRatio < target) {
            // screen is taller than target -> letterbox (bars on top/bottom)
            int idealHeight = Math.round(screenW / target);
            int barHeight = (screenH - idealHeight) / 2;
            if (barHeight > 0) {
                context.fill(0, 0, screenW, barHeight, barColor);
                context.fill(0, screenH - barHeight, screenW, screenH, barColor);
            }
        }
    }

    /** Returns the safe-area top-left inset caused by the current bars, for HUD anchoring. */
    public static int getSafeAreaTopInset(MinecraftClient mc, int screenW, int screenH) {
        GuraConfig cfg = GuraConfig.INSTANCE;
        if (!cfg.aspectRatioLockEnabled) return 0;
        float currentRatio = (float) screenW / screenH;
        if (currentRatio < cfg.targetAspectRatio) {
            int idealHeight = Math.round(screenW / cfg.targetAspectRatio);
            return Math.max(0, (screenH - idealHeight) / 2);
        }
        return 0;
    }
}
