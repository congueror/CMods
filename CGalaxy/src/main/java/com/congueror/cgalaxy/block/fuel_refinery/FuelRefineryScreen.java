package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.CGalaxy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class FuelRefineryScreen extends ContainerScreen<FuelRefineryContainer> {

    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/fuel_refinery.png");
    FuelRefineryContainer container;

    public FuelRefineryScreen(FuelRefineryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        List<ITextComponent> energy_bar = new ArrayList<>();
        energy_bar.add(new TranslationTextComponent("tooltip.cgalaxy.screen.energy_percent").appendString(": " + getEnergyPercent() + "%" + " (" + container.getEnergy() + "FE)"));
        energy_bar.add(new TranslationTextComponent("tooltip.cgalaxy.screen.energy_usage").appendString(": " + container.getEnergyUsage() + "FE/t"));
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        if (mouseX > guiLeft + 170 && mouseX < guiLeft + 188 && mouseY > guiTop + 7 && mouseY < guiTop + 69) {
            this.func_243308_b(matrixStack, energy_bar, mouseX, mouseY);
        }

        for (int i = 0; i < container.getFluidTank().length; i++) {
            FluidTank tank = container.getFluidTank()[i];
            List<ITextComponent> tanks = new ArrayList<>();
            if (!tank.getFluid().isEmpty()) {
                tanks.add(new TranslationTextComponent(tank.getFluid().getTranslationKey()).appendString(": " + tank.getFluid().getAmount() + "mB"));
            } else {
                tanks.add(new TranslationTextComponent("key.cgalaxy.empty"));
            }
            this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
            if (mouseX > guiLeft + 65 && mouseX < guiLeft + 82 && mouseY > guiTop + 16 && mouseY < guiTop + 68 && i == 0) {
                this.func_243308_b(matrixStack, tanks, mouseX, mouseY);
            }
            if (mouseX > guiLeft + 109 && mouseX < guiLeft + 127 && mouseY > guiTop + 16 && mouseY < guiTop + 68 && i == 1) {
                this.func_243308_b(matrixStack, tanks, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        String title = new TranslationTextComponent("gui.cgalaxy.fuel_refinery.title").getString();
        this.font.drawString(matrixStack, title, (xSize / 2 - font.getStringWidth(title) / 2) + 5, 6, 4210752);

        String inv = new TranslationTextComponent("key.categories.inventory").getString();
        this.font.drawString(matrixStack, inv, (xSize / 2 - font.getStringWidth(inv) / 2) - 40, 74, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize + 20, this.ySize);

        //Progress Arrow
        int progress = container.getProgress();
        int processTime = container.getProcessTime();
        int length = processTime > 0 && progress > 0 && progress > processTime ? progress * 24 / processTime : 0;
        this.blit(matrixStack, this.guiLeft + 85, this.guiTop + 35, 196, 0, length + 1, 16);

        //Energy
        int z = 60 - (60 * getEnergyPercent() / 100);
        this.blit(matrixStack, this.guiLeft + 170, this.guiTop + 7 + z, 196, 17, 16, 60 - z);

        this.blit(matrixStack, this.guiLeft + 172, this.guiTop + 9, 212, 17, 16, 60);

        //Fluid Tanks
        if (!container.getFluidTank()[0].getFluid().isEmpty()) {
            int a = 50 - (50 * getFluidPercent(0) / 100);
            String string = container.getFluidTank()[0].getFluid().getFluid().getRegistryName().getPath();
            minecraft.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/block/" + string));
            this.blit(matrixStack, this.guiLeft + 66, this.guiTop + 18 + a, 0, 0, 16, 50 - a);
        }
    }

    private int getEnergyPercent() {
        long currentEnergy = container.getEnergy();
        int maxEnergy = container.getMaxEnergy();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }

    private int getFluidPercent(int tank) {
        long fluid = container.getFluidTank()[tank].getFluidAmount();
        int fluidCapacity = container.getFluidTank()[tank].getCapacity();

        long result = fluid * 100 / fluidCapacity;
        return (int) result;
    }
}
