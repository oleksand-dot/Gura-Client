package com.guraclient.module;

public enum ModuleCategory {
    RENDER("Render"),
    QOL("Quality of Life"),
    HUD("HUD"),
    MOVEMENT("Movement");

    private final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
