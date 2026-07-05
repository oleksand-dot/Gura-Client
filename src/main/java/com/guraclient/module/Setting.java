package com.guraclient.module;

/**
 * A single configurable value belonging to a Module.
 * Kept intentionally simple (double-backed) so it can drive both
 * sliders (min/max/step) and boolean toggles (0.0 / 1.0) in the click GUI.
 */
public class Setting {
    private final String name;
    private double value;
    private final double min;
    private final double max;
    private final double step;
    private final boolean isBoolean;

    private Setting(String name, double value, double min, double max, double step, boolean isBoolean) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
        this.isBoolean = isBoolean;
    }

    public static Setting slider(String name, double defaultValue, double min, double max, double step) {
        return new Setting(name, defaultValue, min, max, step, false);
    }

    public static Setting toggle(String name, boolean defaultValue) {
        return new Setting(name, defaultValue ? 1.0 : 0.0, 0.0, 1.0, 1.0, true);
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public boolean getBooleanValue() {
        return value >= 0.5;
    }

    public int getIntValue() {
        return (int) Math.round(value);
    }

    public void setValue(double value) {
        this.value = Math.max(min, Math.min(max, value));
    }

    public void toggleBoolean() {
        setValue(getBooleanValue() ? 0.0 : 1.0);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }

    public boolean isBoolean() {
        return isBoolean;
    }
}
