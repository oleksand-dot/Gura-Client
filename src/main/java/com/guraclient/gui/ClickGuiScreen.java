package com.guraclient.gui;

import com.guraclient.module.Module;
import com.guraclient.module.ModuleCategory;
import com.guraclient.module.ModuleManager;
import com.guraclient.module.Setting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.List;

/**
 * Simple panel-based click GUI: one column per category, one row per
 * module, toggle button + inline sliders for that module's settings.
 * Panels animate in with a lerp so they don't just snap open (toggle
 * openProgress toward 1 in render()).
 */
public class ClickGuiScreen extends Screen {
    private float openProgress = 0f;

    public ClickGuiScreen() {
        super(Text.literal("Gura Client"));
    }

    @Override
    protected void init() {
        int panelX = 20;
        int panelWidth = 160;
        int gap = 12;

        for (ModuleCategory category : ModuleCategory.values()) {
            List<Module> modules = ModuleManager.getModulesByCategory(category);
            int y = 30;

            for (Module module : modules) {
                int finalX = panelX;
                int finalY = y;
                addDrawableChild(ButtonWidget.builder(
                                Text.literal((module.isEnabled() ? "[ON] " : "[OFF] ") + module.getName()),
                                btn -> {
                                    module.toggle();
                                    btn.setMessage(Text.literal((module.isEnabled() ? "[ON] " : "[OFF] ") + module.getName()));
                                })
                        .dimensions(finalX, finalY, panelWidth, 18)
                        .build());
                y += 20;

                for (Setting setting : module.getSettings()) {
                    if (setting.isBoolean()) {
                        addDrawableChild(ButtonWidget.builder(
                                        Text.literal(setting.getName() + ": " + (setting.getBooleanValue() ? "On" : "Off")),
                                        btn -> {
                                            setting.toggleBoolean();
                                            btn.setMessage(Text.literal(setting.getName() + ": " + (setting.getBooleanValue() ? "On" : "Off")));
                                        })
                                .dimensions(finalX + 10, y, panelWidth - 10, 16)
                                .build());
                    } else {
                        addDrawableChild(new SettingSlider(finalX + 10, y, panelWidth - 10, 16, setting));
                    }
                    y += 18;
                }
                y += 4;
            }
            panelX += panelWidth + gap;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        openProgress = MathHelper.clamp(openProgress + delta * 0.15f, 0f, 1f);

        // dim background behind the panels
        context.fill(0, 0, width, height, (int) (openProgress * 0x90) << 24);

        context.drawCenteredTextWithShadow(textRenderer, "Gura Client", width / 2, 10, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    /** Slider widget bound directly to a Setting. */
    private static class SettingSlider extends SliderWidget {
        private final Setting setting;

        SettingSlider(int x, int y, int width, int height, Setting setting) {
            super(x, y, width, height, Text.literal(label(setting)), progressFor(setting));
            this.setting = setting;
        }

        private static double progressFor(Setting s) {
            return (s.getValue() - s.getMin()) / (s.getMax() - s.getMin());
        }

        private static String label(Setting s) {
            return s.getName() + ": " + s.getValue();
        }

        @Override
        protected void updateMessage() {
            setMessage(Text.literal(label(setting)));
        }

        @Override
        protected void applyValue() {
            double raw = setting.getMin() + value * (setting.getMax() - setting.getMin());
            double stepped = Math.round(raw / setting.getStep()) * setting.getStep();
            setting.setValue(stepped);
        }
    }
              }

