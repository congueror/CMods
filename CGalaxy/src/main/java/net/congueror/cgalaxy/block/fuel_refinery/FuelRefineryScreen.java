package net.congueror.cgalaxy.block.fuel_refinery;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.machine.fluid.AbstractFluidScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class FuelRefineryScreen extends AbstractFluidScreen<FuelRefineryContainer> {
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/fuel_refinery.png");
    FuelRefineryContainer container;

    public FuelRefineryScreen(FuelRefineryContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }

    @Override
    public String getKey() {
        return "block.cgalaxy.fuel_refinery";
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        renderEnergyTooltip(pMatrixStack, pMouseX, pMouseY);
        renderFluidTankTooltip(pMatrixStack, pMouseX, pMouseY, 65, 16, 0);
        renderFluidTankTooltip(pMatrixStack, pMouseX, pMouseY, 109, 16, 1);
        renderStatusTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        renderBackground(pPoseStack, GUI);
        renderHorizontalArrow(pPoseStack, 85, 35, 196, 0);
        renderEnergyBuffer(pPoseStack, 172, 9, 196, 17, 212, 17);
        renderFluidTank(pPoseStack, 66, 18, 0);
        renderFluidTank(pPoseStack, 111, 18, 1);
        renderStatusLight(pPoseStack, 154, 8, 228, 17);
    }
}
