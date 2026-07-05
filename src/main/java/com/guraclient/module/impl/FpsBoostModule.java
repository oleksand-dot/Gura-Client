package com.guraclient.module.impl;

import com.guraclient.module.Module;
import com.guraclient.module.ModuleCategory;
import com.guraclient.module.Setting;

/**
 * Client-side performance tweak — lowers render distance for smoother FPS.
 * Nothing here touches server-visible behavior.
 */
public class FpsBoostModule extends Module {
    private final Setting renderDistance = register(Setting.slider("Render Distance", 8, 2, 32, 1));
    private int savedRenderDistance;

    public FpsBoostModule() {
        super("FPS Boost", "Lowers render distance for smoother FPS", ModuleCategory.QOL, false);
    }

    @Override
    protected void onEnable() {
        if (mc.options != null) {
            savedRenderDistance = mc.options.getViewDistance().getValue();
            mc.options.getViewDistance().setValue(renderDistance.getIntValue());
        }
    }

    @Override
    protected void onDisable() {
        if (mc.options != null) {
            mc.options.getViewDistance().setValue(savedRenderDistance);
        }
    }
}
