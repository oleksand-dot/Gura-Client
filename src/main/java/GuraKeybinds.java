package com.guraclient;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

/**
 * All keybinds live here. On Fold Craft Launcher, keyboard binds still work
 * for external keyboards / controllers mapped to key codes, but FCL also
 * overlays its own touch buttons — you can map the click-GUI open action to
 * one of those in-game via FCL's control editor without touching this code,
 * since it just presses the bound key under the hood.
 */
public final class GuraKeybinds {
    public static KeyBinding openClickGui;

    private GuraKeybinds() {
    }

    public static void register() {
        openClickGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.gura-client.open_click_gui",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_RIGHT_SHIFT,
                "category.gura-client.main"
        ));
    }
}
