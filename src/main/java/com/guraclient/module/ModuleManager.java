package com.guraclient.module;

import com.guraclient.module.impl.FullbrightModule;
import com.guraclient.module.impl.FpsBoostModule;
import com.guraclient.module.impl.ToggleSprintModule;
import com.guraclient.module.impl.ZoomModule;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<Module> MODULES = new ArrayList<>();

    private ModuleManager() {
    }

    /** Call once from the client entrypoint, after keybindings exist. */
    public static void init() {
        register(new ZoomModule());
        register(new FullbrightModule());
        register(new ToggleSprintModule());
        register(new FpsBoostModule());
        // Add new modules here as you build them.
    }

    private static void register(Module module) {
        MODULES.add(module);
    }

    public static List<Module> getModules() {
        return MODULES;
    }

    public static List<Module> getModulesByCategory(ModuleCategory category) {
        return MODULES.stream().filter(m -> m.getCategory() == category).toList();
    }

    /** Poll keybinds (call from ClientTickEvent) and tick enabled modules. */
    public static void onTick() {
        for (Module module : MODULES) {
            KeyBinding kb = module.getKeybind();
            if (kb != null && kb.wasPressed()) {
                module.toggle();
            }
            if (module.isEnabled()) {
                module.onTick();
            }
        }
    }
}
