package net.congueror.cgalaxy.gui.space_suit;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.clib.CLib;
import net.congueror.clib.blocks.abstract_machine.AbstractSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class SpaceSuitSettingsScreen extends AbstractSettingsScreen {

    ResourceLocation blank = new ResourceLocation(CLib.MODID, "textures/gui/blank.png");
    int temperature = 0;
    int radiation = 0;
    int airPressure = 0;
    int color = 0;

    protected SpaceSuitSettingsScreen() {
        super(new TranslatableComponent("gui.cgalaxy.space_suit_settings"), 1);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ImageButton(this.leftPos + 99, this.topPos + 4, 7, 7, 0, 0, 0, blank, width, height, pButton -> {
            Minecraft.getInstance().popGuiLayer();
        }));
        addRenderableWidget(new ImageButton(leftPos + 48, topPos + 24, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (temperature == 2) {
                temperature = 0;
            } else {
                temperature++;
            }
            for (CGConfig.TemperatureUnits m : CGConfig.TemperatureUnits.values()) {
                if (m.ordinal() == temperature) {
                    CGConfig.TEMPERATURE.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 98, topPos + 24, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (temperature == 0) {
                temperature = 2;
            } else {
                temperature--;
            }
            for (CGConfig.TemperatureUnits m : CGConfig.TemperatureUnits.values()) {
                if (m.ordinal() == temperature) {
                    CGConfig.TEMPERATURE.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 48, topPos + 34, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (radiation == 1) {
                radiation = 0;
            } else {
                radiation++;
            }
            for (CGConfig.RadiationUnits m : CGConfig.RadiationUnits.values()) {
                if (m.ordinal() == radiation) {
                    CGConfig.RADIATION.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 98, topPos + 34, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (radiation == 0) {
                radiation = 1;
            } else {
                radiation--;
            }
            for (CGConfig.RadiationUnits m : CGConfig.RadiationUnits.values()) {
                if (m.ordinal() == radiation) {
                    CGConfig.RADIATION.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 48, topPos + 44, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (airPressure == 4) {
                airPressure = 0;
            } else {
                airPressure++;
            }
            for (CGConfig.AirPressureUnits m : CGConfig.AirPressureUnits.values()) {
                if (m.ordinal() == airPressure) {
                    CGConfig.AIR_PRESSURE.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 98, topPos + 44, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (airPressure == 0) {
                airPressure = 4;
            } else {
                airPressure--;
            }
            for (CGConfig.AirPressureUnits m : CGConfig.AirPressureUnits.values()) {
                if (m.ordinal() == airPressure) {
                    CGConfig.AIR_PRESSURE.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 48, topPos + 54, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (color == 1) {
                color = 0;
            } else {
                color++;
            }
            for (CGConfig.GuiColors m : CGConfig.GuiColors.values()) {
                if (m.ordinal() == color) {
                    CGConfig.GUI_COLOR.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 98, topPos + 54, 5, 9, 0, 0, 0, blank, width, height, pButton -> {
            if (color == 0) {
                color = 1;
            } else {
                color--;
            }
            for (CGConfig.GuiColors m : CGConfig.GuiColors.values()) {
                if (m.ordinal() == color) {
                    CGConfig.GUI_COLOR.set(m);
                }
            }
        }));
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        String textureName = CGConfig.GUI_COLOR.get().toString().equals("GREEN") ? "space_suit_gui_settings.png" : CGConfig.GUI_COLOR.get().toString().toLowerCase() + "/" + CGConfig.GUI_COLOR.get().toString().toLowerCase() + "_space_suit_gui_settings.png";
        RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/" + textureName));
        Minecraft mc = Minecraft.getInstance();

        blit(pMatrixStack, leftPos, topPos, 0, 0, 110, 140, 115, 140);

        blit(pMatrixStack, leftPos + 48, topPos + 24, -5, 9, 5, 9, 115, 140);
        blit(pMatrixStack, leftPos + 98, topPos + 24, -5, 0, 5, 9, 115, 140);

        blit(pMatrixStack, leftPos + 48, topPos + 34, -5, 9, 5, 9, 115, 140);
        blit(pMatrixStack, leftPos + 98, topPos + 34, -5, 0, 5, 9, 115, 140);

        blit(pMatrixStack, leftPos + 48, topPos + 44, -5, 9, 5, 9, 115, 140);
        blit(pMatrixStack, leftPos + 98, topPos + 44, -5, 0, 5, 9, 115, 140);

        blit(pMatrixStack, leftPos + 48, topPos + 54, -5, 9, 5, 9, 115, 140);
        blit(pMatrixStack, leftPos + 98, topPos + 54, -5, 0, 5, 9, 115, 140);

        int textColor = CGConfig.GUI_COLOR.get().rgb;
        pMatrixStack.pushPose();
        pMatrixStack.scale(0.6f, 0.6f, 0.6f);
        int x = (int) ((leftPos) / 0.6 + (6 / 0.6));
        int y = (int) (((topPos) / 0.6) + (26 / 0.6));
        drawString(pMatrixStack, mc.font, "Temperature: ", x, y, textColor);
        drawCenteredString(pMatrixStack, mc.font, CGConfig.TEMPERATURE.get().toString(), x + 117, y, textColor);

        drawString(pMatrixStack, mc.font, "Radiation: ", x, y + 16, textColor);
        drawCenteredString(pMatrixStack, mc.font, CGConfig.RADIATION.get().toString(), x + 117, y + 16, textColor);

        drawString(pMatrixStack, mc.font, "Air Pressure: ", x, y + 32, textColor);
        drawCenteredString(pMatrixStack, mc.font, CGConfig.AIR_PRESSURE.get().toString(), x + 117, y + 32, textColor);

        drawString(pMatrixStack, mc.font, "Color: ", x, y + 48, textColor);
        drawCenteredString(pMatrixStack, mc.font, CGConfig.GUI_COLOR.get().toString(), x + 117, y + 48, textColor);
        pMatrixStack.popPose();
    }
}