package net.congueror.cgalaxy.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGFluidInit;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.fluids.FluidStack;

public class RocketHUDOverlay extends GuiComponent implements IIngameOverlay {
    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            CGDimensionBuilder.DimensionObject obj = CGDimensionBuilder.getObjectFromKey(player.level.dimension());
            if (obj != null && player.getVehicle() instanceof AbstractRocket rocket) {
                RenderSystem.setShaderTexture(0, obj.getYOverlayTexture());
                blit(mStack, 0, 100, 0, 0, 16, 102, 26, 102);
                double y = player.getY();
                if (y <= 400 && y >= 48) {
                    blit(mStack, 3, 202 - (int) (y / 4), 16, 0, 10, 10, 26, 102);
                } else if (y >= 400) {
                    blit(mStack, 3, 202 - (400 / 4), 16, 10, 10, 10, 26, 102);
                } else if (y <= 48) {
                    blit(mStack, 3, 202 - (48 / 4), 16, 20, 10, 10, 26, 102);
                }
                String coords = "x: " + (int) player.getX() + ", y: " + (int) player.getY() + ", z:" + (int) player.getZ();
                int k = mc.font.width(coords);
                int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                FluidStack stack1 = new FluidStack(CGFluidInit.KEROSENE.getStill().get(), rocket.getFuel());
                int a = 51 - (51 * MathHelper.getPercent(rocket.getFuel(), rocket.getCapacity()) / 100);
                int x2 = l - 3;
                int y1 = 1 + a;
                int y2 = y1 + (51 - a);
                RenderingHelper.renderFluid(mStack.last().pose(), stack1, l - 19, x2, y1, y2, 0);
                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/rocket_fuel.png"));
                blit(mStack, l - 20, 0, 0, 0, 18, 53, 18, 53);
            }
        }
    }
}
