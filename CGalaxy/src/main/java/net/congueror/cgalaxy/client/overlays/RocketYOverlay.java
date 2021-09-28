package net.congueror.cgalaxy.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.item.SpaceSuitItem;
import net.congueror.cgalaxy.world.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class RocketYOverlay extends GuiComponent implements IIngameOverlay {
    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            if (player.getVehicle() instanceof RocketEntity) {
                String texture = "textures/gui/rocket_y_hud.png";
                if (player.level.dimension().equals(Dimensions.MOON)) {
                    texture = "textures/gui/rocket_y_hud_moon.png";
                }
                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, texture));
                blit(mStack, 0, 100, 0, 0, 16, 102, 26, 102);
                double y = player.getY();
                if (y <= 400 && y >= 48) {
                    blit(mStack, 3, 202 - (int) (y / 4), 16, 0, 10, 10, 26, 102);
                } else if (y >= 400) {
                    blit(mStack, 3, 202 - (400 / 4), 16, 10, 10, 10, 26, 102);
                } else if (y <= 48) {
                    blit(mStack, 3, 202 - (48 / 4), 16, 20, 10, 10, 26, 102);
                }
            }
        }
    }
}
