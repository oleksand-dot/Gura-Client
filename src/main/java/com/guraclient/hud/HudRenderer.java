package com.guraclient.hud;

import com.guraclient.config.GuraConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

/**
 * Draws the restyled HUD (FPS / coords / ping). Registered against
 * Fabric API's HudRenderCallback from GuraClient#onInitializeClient.
 *
 * Positions are anchor-relative (top-left corner + padding) rather than
 * fixed pixels, so it holds up across phone aspect ratios and the
 * letterboxing applied by AspectRatioRenderer.
 */
public class HudRenderer {
    private static float fadeIn = 0f;

    public static void render(DrawContext context, MinecraftClient mc) {
        GuraConfig cfg = GuraConfig.INSTANCE;
        if (mc.player == null || mc.options.hudHidden) return;

        // simple lerp-based fade-in so the HUD doesn't just snap into view
        fadeIn = MathHelper.clamp(fadeIn + 0.08f, 0f, 1f);
        int alpha = (int) (fadeIn * 255) << 24;

        int x = 6;
        int y = 6;
        int lineHeight = (int) (10 * cfg.hudScale);
        int color = 0xFFFFFF | alpha;

        if (cfg.hudShowFps) {
            context.drawText(mc.textRenderer, Text.literal(mc.getCurrentFps() + " FPS"), x, y, color, true);
            y += lineHeight;
        }
        if (cfg.hudShowCoords && mc.player != null) {
            String coords = String.format("XYZ: %.1f / %.1f / %.1f",
                    mc.player.getX(), mc.player.getY(), mc.player.getZ());
            context.drawText(mc.textRenderer, Text.literal(coords), x, y, color, true);
            y += lineHeight;
        }
        if (cfg.hudShowPing && mc.getNetworkHandler() != null && mc.player != null) {
            PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
            int ping = entry != null ? entry.getLatency() : -1;
            if (ping >= 0) {
                context.drawText(mc.textRenderer, Text.literal(ping + " ms"), x, y, color, true);
            }
        }
    }
}
