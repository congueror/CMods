package net.congueror.cgalaxy.blocks.fuel_loader;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.machine.fluid.AbstractFluidScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class FuelLoaderScreen extends AbstractFluidScreen<FuelLoaderContainer> {
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/fuel_loader.png");
    FuelLoaderContainer container;

    public FuelLoaderScreen(FuelLoaderContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }

    @Override
    public String getKey() {
        return "block.cgalaxy.fuel_loader";
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTicks);
        renderEnergyTooltip(pPoseStack, pMouseX, pMouseY);
        renderFluidTankTooltip(pPoseStack, pMouseX, pMouseY, 99, 16, 0);
        renderStatusTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        renderBackground(pPoseStack, GUI);
        renderVerticalArrow(pPoseStack, 82, 59, 228, 30);
        renderEnergyBuffer(pPoseStack, 172, 9, 196, 0, 212, 0);
        renderFluidTank(pPoseStack, 100, 18, 0);
        renderStatusLight(pPoseStack, 154, 8, 245, 0);
    }
}
