package net.congueror.clib.api.machine.fluid;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFluidScreen<T extends AbstractFluidContainer<?>> extends AbstractContainerScreen<T> {

    T container;

    public AbstractFluidScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }

    /**
     * The name that will be displayed on the screen.
     */
    public abstract String getKey();

    /**
     * Renders a tooltip that gives information about the status of the machine at the coordinates given.
     *
     * @param x The starting x position of the status light
     * @param y The starting y position (bottom) of the status light
     */
    public void renderStatusTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        List<Component> status = new ArrayList<>();
        status.add(new TranslatableComponent(container.getInfo()));
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        if (pMouseX > leftPos + x && pMouseX < leftPos + (x + 7) && pMouseY > topPos + y && pMouseY < topPos + (y + 7)) {
            this.renderComponentTooltip(pPoseStack, status, pMouseX, pMouseY);
        }
    }

    /**
     * Renders a tooltip that gives information about the status of the machine at the default location, also see {@link #renderEnergyTooltip(PoseStack, int, int, int, int)}
     */
    public void renderStatusTooltip(PoseStack poseStack, int pMouseX, int pMouseY) {
        renderStatusTooltip(poseStack, pMouseX, pMouseY, 153, 7);
    }

    /**
     * Renders a tooltip consisting of the energy buffer's energy and usage at the coordinates given.
     *
     * @param x The starting x position of the buffer
     * @param y The starting y position (bottom) of the buffer
     */
    public void renderEnergyTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        List<Component> energy_bar = new ArrayList<>();
        energy_bar.add(new TranslatableComponent("tooltip.cgalaxy.screen.energy_percent").append(": " + MathHelper.getPercent(container.getEnergy(), container.getMaxEnergy()) + "%" + " (" + container.getEnergy() + "FE)"));
        energy_bar.add(new TranslatableComponent("tooltip.cgalaxy.screen.energy_usage").append(": " + container.getEnergyUsage() + "FE/t"));
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        if (pMouseX > leftPos + x && pMouseX < leftPos + (x + 18) && pMouseY > topPos + y && pMouseY < topPos + (y + 62)) {
            this.renderComponentTooltip(pPoseStack, energy_bar, pMouseX, pMouseY);
        }
    }

    /**
     * Renders a tooltip consisting of the energy buffer's energy and usage at the default location, also see {@link #renderEnergyTooltip(PoseStack, int, int, int, int)}
     */
    public void renderEnergyTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        renderEnergyTooltip(pPoseStack, pMouseX, pMouseY, 170, 7);
    }

    /**
     * Renders a tooltip consisting of the fluid tank's fluid and amount at the coordinates given.
     *
     * @param x The starting x position of the tank.
     * @param y The starting y position (bottom) of the tank.
     */
    public void renderFluidTankTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, int tankIndex) {
        FluidTank tank = container.getFluidTanks()[tankIndex];
        List<Component> tanks = new ArrayList<>();
        if (!tank.getFluid().isEmpty()) {
            tanks.add(new TranslatableComponent(tank.getFluid().getTranslationKey()).append(": " + tank.getFluid().getAmount() + "mB"));
        } else {
            tanks.add(new TranslatableComponent("key.cgalaxy.empty"));
        }
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        if (pMouseX > leftPos + x && pMouseX < leftPos + (x + 16) && pMouseY > topPos + y && pMouseY < topPos + (y + 52)) {
            this.renderComponentTooltip(pPoseStack, tanks, pMouseX, pMouseY);
        }
    }

    /**
     * Renders the gui texture in the background. Make sure this is called first inside {@link #renderBg(PoseStack, float, int, int)}.
     *
     * @param gui The location of the texture.
     */
    public void renderBackground(PoseStack poseStack, ResourceLocation gui) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, gui);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth + 20, this.imageHeight);
    }

    /**
     * Renders the color of the status light.
     *
     * @param x The starting x position of the light.
     * @param y The starting y position of the light.
     */
    public void renderStatusLight(PoseStack poseStack, int x, int y, int uOffset, int vOffset) {
        if (container.getInfo().contains("working")) {
            this.blit(poseStack, this.leftPos + x, this.topPos + y, uOffset, vOffset, 7, 7);
        } else if (container.getInfo().contains("idle")) {
            this.blit(poseStack, this.leftPos + x, this.topPos + y, uOffset, vOffset + 14, 7, 7);
        } else if (container.getInfo().contains("error")) {
            this.blit(poseStack, this.leftPos + x, this.topPos + y, uOffset, vOffset + 7, 7, 7);
        }
    }

    /**
     * Renders a horizontal progress arrow.
     *
     * @param x       The starting x position of the arrow.
     * @param y       The starting y position (bottom) of the arrow.
     * @param uOffset The texture's x location of the arrow
     * @param vOffset The texture's y location of the arrow
     */
    public void renderHorizontalArrow(PoseStack poseStack, int x, int y, int uOffset, int vOffset) {
        int progress = container.getProgress();
        int processTime = container.getProcessTime();
        int length = processTime > 0 && progress > 0 ? progress * 24 / processTime : 0;
        this.blit(poseStack, this.leftPos + x, this.topPos + y, uOffset, vOffset, length + 1, 16);
    }

    /**
     * Renders a vertical progress arrow.
     *
     * @param x       The starting x position of the arrow.
     * @param y       The starting y position (bottom) of the arrow.
     * @param uOffset The texture's x location of the arrow
     * @param vOffset The texture's y location of the arrow
     */
    public void renderVerticalArrow(PoseStack poseStack, int x, int y, int uOffset, int vOffset) {
        int progress = container.getProgress();
        int processTime = container.getProcessTime();
        int length = processTime > 0 && progress > 0 ? progress * 30 / processTime : 0;
        this.blit(poseStack, this.leftPos + x, this.topPos + y - length, uOffset, vOffset - length, 16, length + 1);
    }

    /**
     * Renders an energy buffer with the stored energy at the given coordinates.
     *
     * @param x             The starting x position of the buffer.
     * @param y             The starting y position (bottom) of the buffer.
     * @param uEnergyOffset The texture's x location of the energy
     * @param vEnergyOffset The texture's y location of the energy
     * @param uGlassOffset  The texture's x location of the glass overlay
     * @param vGlassOffset  The texture's y location of the glass overlay
     */
    public void renderEnergyBuffer(PoseStack pPoseStack, int x, int y, int uEnergyOffset, int vEnergyOffset, int uGlassOffset, int vGlassOffset) {
        int z = 60 - (60 * MathHelper.getPercent(container.getEnergy(), container.getMaxEnergy()) / 100);
        this.blit(pPoseStack, this.leftPos + x, this.topPos + y + z, uEnergyOffset, vEnergyOffset, 16, 60 - z);
        this.blit(pPoseStack, this.leftPos + x, this.topPos + y, uGlassOffset, vGlassOffset, 16, 60);
    }

    /**
     * Renders a fluid tank with the fluid at the given coordinates.
     *
     * @param x The starting x position of the tank.
     * @param y The starting y position (bottom) of the tank.
     */
    public void renderFluidTank(PoseStack pPoseStack, int x, int y, int tankIndex) {
        if (!container.getFluidTanks()[tankIndex].getFluid().isEmpty()) {
            Matrix4f matrix = pPoseStack.last().pose();
            int a = 50 - (50 * MathHelper.getPercent(container.getFluidTanks()[tankIndex].getFluidAmount(), container.getFluidTanks()[tankIndex].getCapacity()) / 100);
            int x1 = this.leftPos + x;
            int x2 = x1 + 16;
            int y1 = this.topPos + y + a;
            int y2 = y1 + (50 - a);
            RenderingHelper.renderFluid(matrix, container.getFluidTanks()[tankIndex].getFluid(), x1, x2, y1, y2, this.getBlitOffset());
        }
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        String title = new TranslatableComponent(getKey()).getString();
        this.font.draw(pPoseStack, title, ((float) (imageWidth / 2 - font.width(title) / 2)) + 5, 6, 4210752);

        String inv = new TranslatableComponent("key.categories.inventory").getString();
        this.font.draw(pPoseStack, inv, ((float) (imageWidth / 2 - font.width(inv) / 2)) - 30, 74, 4210752);
    }
}
