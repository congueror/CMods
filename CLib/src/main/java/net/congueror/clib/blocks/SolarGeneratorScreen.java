package net.congueror.clib.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.clib.CLib;
import net.congueror.clib.api.machine.item.AbstractItemScreen;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class SolarGeneratorScreen extends AbstractItemScreen<SolarGeneratorContainer> {
    public static ResourceLocation GUI = new ResourceLocation(CLib.MODID, "textures/gui/solar_generator.png");

    public SolarGeneratorScreen(SolarGeneratorContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public String getKey() {
        return "block.clib.solar_generator";
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTicks);
        renderTooltip(pPoseStack, pMouseX, pMouseY, 117, 8, 18, 62,
                new TranslatableComponent("key.cgalaxy.energy_percent").append(": " + MathHelper.getPercent(container.getEnergy(), container.getMaxEnergy()) + "%" + " (" + container.getEnergy() + "FE)"),
                new TranslatableComponent("key.cgalaxy.energy_generation").append(": " + container.getEnergyGen() + "FE/t"));
        renderStatusTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        renderBackground(poseStack, GUI);
        if (container.getInfo().contains("working"))
            this.blit(poseStack, this.leftPos + 88, this.topPos + 33, 196, 60, 25, 16);
        renderEnergyBuffer(poseStack, 118, 9, 196, 0, 212, 0);
        renderStatusLight(poseStack, 154, 8, 245, 0);

        RenderSystem.setShaderTexture(0, container.texture());
        int offset = RenderingHelper.isDayTime(minecraft.level) ? 16 : 0;
        blit(poseStack, leftPos + 64, topPos + 33, 0, offset, 16, 16, 16, 32);
    }
}
