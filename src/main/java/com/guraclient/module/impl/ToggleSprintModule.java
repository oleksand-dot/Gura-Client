package com.guraclient.module.impl;

import com.guraclient.module.Module;
import com.guraclient.module.ModuleCategory;

/**
 * QoL module: automatically sprints while moving forward so touch-screen
 * players don't need a dedicated sprint gesture.
 */
public class ToggleSprintModule extends Module {

    public ToggleSprintModule() {
        super("Toggle Sprint", "Automatically sprints while walking forward", ModuleCategory.QOL, false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.options == null) return;
        boolean movingForward = mc.options.forwardKey.isPressed();
        if (movingForward && !mc.player.isSprinting() && mc.player.getHungerManager().getFoodLevel() > 6) {
            mc.player.setSprinting(true);
        }
    }
}
