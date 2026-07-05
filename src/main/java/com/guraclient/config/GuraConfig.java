package com.guraclient.config;

/**
 * Simple in-memory config. Swap the fields below for a Gson-backed
 * load/save to a file in .minecraft/config/gura-client.json once you're
 * ready to persist settings across launches.
 */
public class GuraConfig {
    public static final GuraConfig INSTANCE = new GuraConfig();

    // --- Crosshair ---
    public enum CrosshairShape { CROSS, DOT, CIRCLE }

    public CrosshairShape crosshairShape = CrosshairShape.CROSS;
    public boolean crosshairEnabled = true;
    public float crosshairR = 1.0f;
    public float crosshairG = 1.0f;
    public float crosshairB = 1.0f;
    public float crosshairThickness = 1.5f;
    public boolean crosshairOutline = true;
    public boolean crosshairDynamicOpacity = true;

    // --- Aspect ratio ---
    public boolean aspectRatioLockEnabled = false;
    /** e.g. 16f/9f, 4f/3f, 21f/9f */
    public float targetAspectRatio = 16f / 9f;

    // --- HUD ---
    public boolean hudShowFps = true;
    public boolean hudShowCoords = true;
    public boolean hudShowPing = true;
    public float hudScale = 1.0f;

    private GuraConfig() {
    }
}
