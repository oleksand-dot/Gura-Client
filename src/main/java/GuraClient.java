package com.guraclient;

import com.guraclient.gui.ClickGuiScreen;
import com.guraclient.hud.AspectRatioRenderer;
import com.guraclient.hud.CrosshairRenderer;
import com.guraclient.hud.HudRenderer;
import com.guraclient.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

/**
 * Entry point for Gura Client (client-side only mod).
 * Registered via fabric.mod.json -> entrypoints.client
 */
public class GuraClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GuraKeybinds.register();
        ModuleManager.init();

        // Tick: poll keybinds + tick enabled modules
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ModuleManager.onTick();

            if (GuraKeybinds.openClickGui.wasPressed() && client.currentScreen == null) {
                client.setScreen(new ClickGuiScreen());
            }
        });

        // HUD: draw letterbox bars, then custom HUD text, then custom crosshair, each frame
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            var client = net.minecraft.client.MinecraftClient.getInstance();
            AspectRatioRenderer.render(drawContext, client);
            HudRenderer.render(drawContext, client);
            CrosshairRenderer.render(drawContext, client);
        });
    }
}
