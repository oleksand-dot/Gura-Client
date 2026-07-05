package com.guraclient.hud;

import com.guraclient.config.GuraConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

/**
 * Replaces the vanilla crosshair. The vanilla one is cancelled via
 * CrosshairMixin; this class draws the custom version on top, anchored
 * to the exact center of the (possibly letterboxed) viewport.
 */
public class CrosshairRenderer {

    public static void render(DrawContext context, MinecraftClient mc) {
        GuraConfig cfg = GuraConfig.INSTANCE;
        if (!cfg.crosshairEnabled || mc.options.hudHidden) return;

        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2;

        float opacity = 1.0f;
        if (cfg.crosshairDynamicOpacity && mc.crosshairTarget != null) {
            // fade slightly when actively targeting a block/entity so it doesn't obscure aim
            opacity = mc.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.MISS ? 1.0f : 0.6f;
        }

        int a = (int) (opacity * 255);
        int color = (a << 24) | (toByte(cfg.crosshairR) << 16) | (toByte(cfg.crosshairG) << 8) | toByte(cfg.crosshairB);
        int outlineColor = (a << 24); // black outline, alpha-matched

        int thickness = Math.max(1, Math.round(cfg.crosshairThickness));

        switch (cfg.crosshairShape) {
            case CROSS -> drawCross(context, centerX, centerY, thickness, color, outlineColor, cfg.crosshairOutline);
            case DOT -> drawDot(context, centerX, centerY, thickness, color, outlineColor, cfg.crosshairOutline);
            case CIRCLE -> drawCircle(context, centerX, centerY, thickness, color, outlineColor, cfg.crosshairOutline);
        }
    }

    private static int toByte(float f) {
        return Math.max(0, Math.min(255, Math.round(f * 255)));
    }

    private static void drawCross(DrawContext ctx, int cx, int cy, int t, int color, int outline, boolean drawOutline) {
        int length = 5 + t;
        int gap = 2;
        if (drawOutline) {
            ctx.fill(cx - length - 1, cy - t / 2 - 1, cx - gap + 1, cy + t / 2 + 1, outline);
            ctx.fill(cx + gap - 1, cy - t / 2 - 1, cx + length + 1, cy + t / 2 + 1, outline);
            ctx.fill(cx - t / 2 - 1, cy - length - 1, cx + t / 2 + 1, cy - gap + 1, outline);
            ctx.fill(cx - t / 2 - 1, cy + gap - 1, cx + t / 2 + 1, cy + length + 1, outline);
        }
        // horizontal arms
        ctx.fill(cx - length, cy - t / 2, cx - gap, cy + t / 2, color);
        ctx.fill(cx + gap, cy - t / 2, cx + length, cy + t / 2, color);
        // vertical arms
        ctx.fill(cx - t / 2, cy - length, cx + t / 2, cy - gap, color);
        ctx.fill(cx - t / 2, cy + gap, cx + t / 2, cy + length, color);
    }

    private static void drawDot(DrawContext ctx, int cx, int cy, int t, int color, int outline, boolean drawOutline) {
        int r = Math.max(1, t);
        if (drawOutline) {
            ctx.fill(cx - r - 1, cy - r - 1, cx + r + 1, cy + r + 1, outline);
        }
        ctx.fill(cx - r, cy - r, cx + r, cy + r, color);
    }

    private static void drawCircle(DrawContext ctx, int cx, int cy, int t, int color, int outline, boolean drawOutline) {
        int radius = 4 + t;
        int steps = 24;
        for (int i = 0; i < steps; i++) {
            double angle = (2 * Math.PI * i) / steps;
            int px = cx + (int) (Math.cos(angle) * radius);
            int py = cy + (int) (Math.sin(angle) * radius);
            if (drawOutline) {
                ctx.fill(px - t, py - t, px + t + 1, py + t + 1, outline);
            }
            ctx.fill(px - t + 1, py - t + 1, px + t, py + t, color);
        }
    }
}
