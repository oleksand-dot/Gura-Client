package com.guraclient.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for every toggleable feature in Gura Client.
 * Subclass this and override onEnable/onDisable/onTick as needed,
 * then register the instance in ModuleManager.
 */
public abstract class Module {
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final ModuleCategory category;
    private final List<Setting> settings = new ArrayList<>();
    private KeyBinding keybind;
    private boolean enabled;

    protected Module(String name, String description, ModuleCategory category, boolean enabledByDefault) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = enabledByDefault;
    }

    public final void toggle() {
        setEnabled(!enabled);
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /** Called every client tick while the module is enabled. Override if needed. */
    public void onTick() {
    }

    /** Called once when the module is toggled on. */
    protected void onEnable() {
    }

    /** Called once when the module is toggled off. */
    protected void onDisable() {
    }

    protected Setting register(Setting setting) {
        settings.add(setting);
        return setting;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public KeyBinding getKeybind() {
        return keybind;
    }

    public void setKeybind(KeyBinding keybind) {
        this.keybind = keybind;
    }
}
