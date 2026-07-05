package com.guraclient.module.impl;

import com.guraclient.module.Module;
import com.guraclient.module.ModuleCategory;

/**
 * Purely cosmetic brightness override — client-side rendering only,
 * does not reveal anything the server wouldn't already send you.
 */
public class FullbrightModule extends Module {
    private double savedGamma = 1.0;

    public FullbrightModule() {
        super("Fullbright", "Maxes out brightness while enabled", ModuleCategory.RENDER, false);
    }

    @Override
    protected void onEnable() {
        if (mc.options != null) {
            savedGamma = mc.options.getGamma().getValue();
            mc.options.getGamma().setValue(15.0); // vanilla gamma slider caps around here internally
        }
    }

    @Override
    protected void onDisable() {
        if (mc.options != null) {
            mc.options.getGamma().setValue(savedGamma);
        }
    }
}
