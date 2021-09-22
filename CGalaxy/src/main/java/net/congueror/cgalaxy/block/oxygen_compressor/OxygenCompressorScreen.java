package net.congueror.cgalaxy.block.oxygen_compressor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.machine.fluid.AbstractFluidScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class OxygenCompressorScreen extends AbstractFluidScreen<OxygenCompressorContainer> {
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/oxygen_compressor.png");
    OxygenCompressorContainer container;

    public OxygenCompressorScreen(OxygenCompressorContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }

    @Override
    public String getKey() {
        return "block.cgalaxy.oxygen_compressor";
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTicks);
        renderEnergyTooltip(pPoseStack, pMouseX, pMouseY);
        renderFluidTankTooltip(pPoseStack, pMouseX, pMouseY, 101, 17, 0);
        renderStatusTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        renderBackground(pPoseStack, GUI);
        renderHorizontalArrow(pPoseStack, 77, 38, 196, 0);
        renderEnergyBuffer(pPoseStack, 172, 9, 196, 17, 212, 17);
        renderFluidTank(pPoseStack, 102, 18, 0);
        renderStatusLight(pPoseStack, 154, 8, 228, 17);
    }
}
