package net.congueror.cgalaxy.gui.space_suit;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.clib.blocks.machine_base.AbstractSettingsScreen;
import net.congueror.clib.util.MathHelper;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class SpaceSuitSettingsScreen extends AbstractSettingsScreen {

    int i;
    //Integer[] : 0 = x, 1 = y, 2 = color, 3 = index
    ArrayList<BiConsumer<PoseStack, Integer[]>> strings = new ArrayList<>();

    protected SpaceSuitSettingsScreen() {
        super(new TranslatableComponent("gui.cgalaxy.space_suit_settings"), 1);
    }

    @Override
    protected void init() {
        super.init();

        addButton(CGConfig.TEMPERATURE, CGConfig.TemperatureUnits.values(), "Temperature");
        addButton(CGConfig.RADIATION, CGConfig.RadiationUnits.values(), "Radiation");
        addButton(CGConfig.AIR_PRESSURE, CGConfig.AirPressureUnits.values(), "Air Pressure");
        addButton(CGConfig.GUI_COLOR, CGConfig.GuiColors.values(), "Color");
    }

    private <T extends Enum<T>> void addButton(ForgeConfigSpec.EnumValue<T> enumValue, T[] values, String name) {
        addRenderableWidget(new ImageButton(leftPos + 48, topPos + 24 + (11 * i), 5, 9, 110, 9, 0, TEXTURE, 115, 140, pButton -> {
            int value = enumValue.get().ordinal();
            if (value == values.length - 1) {
                value = 0;
            } else {
                value++;
            }

            for (T m : values) {
                if (m.ordinal() == value) {
                    enumValue.set(m);
                }
            }
        }));
        addRenderableWidget(new ImageButton(leftPos + 98, topPos + 24 + (11 * i), 5, 9, 110, 0, 0, TEXTURE, 115, 140, pButton -> {
            int value = enumValue.get().ordinal();
            if (value == 0) {
                value = values.length - 1;
            } else {
                value--;
            }

            for (T m : values) {
                if (m.ordinal() == value) {
                    enumValue.set(m);
                }
            }
        }));

        strings.add((poseStack, a) -> {
            drawString(poseStack, minecraft.font, name + ": ", a[0], a[1] + (18 * a[3]), a[2]);
            drawCenteredString(poseStack, minecraft.font, enumValue.get().toString(), a[0] + 117, a[1] + (18 * a[3]), a[2]);
        });
        i++;
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        int textColor = CGConfig.GUI_COLOR.get().rgb;
        int[] backgroundColor = MathHelper.DecimalRGBtoRGB(textColor);
        RenderSystem.setShaderColor(backgroundColor[0] / 255f, backgroundColor[1] / 255f, backgroundColor[2] / 255f, 0.65f);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);

        textColor = MathHelper.RGBtoDecimalRGB(Math.min(255, backgroundColor[0] * 2), Math.min(255, backgroundColor[1] * 2), Math.min(255, backgroundColor[2] * 2));
        pMatrixStack.pushPose();
        pMatrixStack.scale(0.6f, 0.6f, 0.6f);
        int x = (int) ((leftPos) / 0.6 + (6 / 0.6));
        int y = (int) (((topPos) / 0.6) + (26 / 0.6));
        for (int i = 0; i < strings.size(); i++) {
            strings.get(i).accept(pMatrixStack, new Integer[] {x, y, textColor, i});
        }
        pMatrixStack.popPose();
    }
}