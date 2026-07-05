package com.guraclient.module.impl;

import com.guraclient.module.Module;
import com.guraclient.module.ModuleCategory;
import com.guraclient.module.Setting;
import net.minecraft.client.option.ParticlesOption;

/**
 * Client-side performance tweaks — lowers particle density and entity
 * shadow/distance related render settings. Nothing here touches
 * server-visible behavior.
 */
public class FpsBoostModule extends Module {
    private final Setting renderDistance = register(Setting.slider("Render Distance", 8, 2, 32, 1));
    private int savedRenderDistance;
    private ParticlesOption savedParticles;

    public FpsBoostModule() {
        super("FPS Boost", "Lowers particles and render distance for smoother FPS", ModuleCategory.QOL, false);
    }

    @Override
    protected void onEnable() {
        if (mc.options != null) {
            savedRenderDistance = mc.options.getViewDistance().getValue();
            savedParticles = mc.options.getParticles().getValue();
            mc.options.getViewDistance().setValue(renderDistance.getIntValue());
            mc.options.getParticles().setValue(ParticlesOption.MINIMAL);
        }
    }

    @Override
    protected void onDisable() {
        if (mc.options != null) {
            mc.options.getViewDistance().setValue(savedRenderDistance);
            mc.options.getParticles().setValue(savedParticles);
        }
    }
}
