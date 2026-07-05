package com.guraclient.module.impl;

import com.guraclient.module.Module;
import com.guraclient.module.ModuleCategory;
import com.guraclient.module.Setting;

/**
 * Cosmetic FOV-based zoom. Purely a client-side rendering tweak —
 * it does not change hitboxes, reach, or anything server-visible.
 */
public class ZoomModule extends Module {
    private final Setting zoomFov = register(Setting.slider("Zoom FOV", 20.0, 5.0, 60.0, 1.0));
    private double previousFov = -1;

    public ZoomModule() {
        super("Zoom", "Hold to zoom in your view (cosmetic FOV change only)", ModuleCategory.RENDER, false);
    }

    @Override
    public void onTick() {
        if (mc.options == null) return;
        if (previousFov < 0) {
            previousFov = mc.options.getFov().getValue();
        }
        // Actual FOV override should be applied in a GameRenderer FOV mixin/event;
        // this field just tracks the target value the renderer hook should read.
    }

    public double getZoomFov() {
        return zoomFov.getValue();
    }

    @Override
    protected void onDisable() {
        previousFov = -1;
    }
}
