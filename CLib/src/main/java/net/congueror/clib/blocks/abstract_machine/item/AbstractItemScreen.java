package net.congueror.clib.blocks.abstract_machine.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.clib.CLib;
import net.congueror.clib.util.MathHelper;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractItemScreen<T extends AbstractItemContainer<?>> extends AbstractContainerScreen<T> {
    protected T container;
    public static final int elementWidth = 56;
    public static final int elementHeight = 60;

    public AbstractItemScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }

    /**
     * The name that will be displayed on the screen.
     */
    public abstract String getKey();

    /**
     * Renders a tooltip consisting of the messages passed in, at the coordinates given.
     *
     * @param x        The starting x position of the area
     * @param y        The starting y position of the area
     * @param width    The width of the area
     * @param height   The Height of the area
     * @param messages The messages that will be displayed on the tooltip
     */
    public void renderTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, int width, int height, Component... messages) {
        List<Component> lns = new ArrayList<>(Arrays.asList(messages));
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        if (pMouseX > leftPos + x && pMouseX < leftPos + (x + width) && pMouseY > topPos + y && pMouseY < topPos + (y + height)) {
            this.renderComponentTooltip(pPoseStack, lns, pMouseX, pMouseY);
        }
    }

    /**
     * Renders a tooltip that gives information about the status of the machine at the coordinates given.
     */
    public void renderStatusTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        Component com;
        if (container.getInfo().contains(",")) {
            String comp = container.getInfo().substring(0, container.getInfo().indexOf(","));
            com = new TranslatableComponent(comp).append(container.getInfo().substring(container.getInfo().indexOf(",")));
        } else {
            com = new TranslatableComponent(container.getInfo());
        }

        renderTooltip(pPoseStack, pMouseX, pMouseY, 153, 7, 7, 7, com);
    }

    /**
     * Renders a tooltip consisting of the energy buffer's energy and usage at the coordinates given.
     */
    public void renderEnergyTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        renderTooltip(pPoseStack, pMouseX, pMouseY, 170, 7, 18, 62,
                new TranslatableComponent("key.clib.energy_percent").append(": " + MathHelper.getPercent(container.getEnergy(), container.getMaxEnergy()) + "%" + " (" + container.getEnergy() + "FE)"),
                new TranslatableComponent("key.clib.energy_usage").append(": " + container.getEnergyUsage() + "FE/t"));
    }

    /**
     * Renders the gui modTexture in the background. Make sure this is called first inside {@link #renderBg(PoseStack, float, int, int)}.
     *
     * @param gui The location of the modTexture.
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
    public void renderStatusLight(PoseStack poseStack, int x, int y) {
        setElementTex();
        if (container.getInfo().contains("working")) {
            blit(poseStack, this.leftPos + x, this.topPos + y, 32, 0, 7, 7, elementWidth, elementHeight);
        } else if (container.getInfo().contains("idle")) {
            blit(poseStack, this.leftPos + x, this.topPos + y, 32, 14, 7, 7, elementWidth, elementHeight);
        } else if (container.getInfo().contains("error")) {
            blit(poseStack, this.leftPos + x, this.topPos + y, 32, 7, 7, 7, elementWidth, elementHeight);
        }
    }

    /**
     * Renders a horizontal progress arrow.
     *
     * @param x The starting x position of the arrow.
     * @param y The starting y position (bottom) of the arrow.
     */
    public void renderHorizontalArrow(PoseStack poseStack, int x, int y) {
        setElementTex();
        int progress = container.getProgress();
        int processTime = container.getProcessTime();
        int length = processTime > 0 && progress > 0 ? progress * 24 / processTime : 0;
        blit(poseStack, this.leftPos + x, this.topPos + y, 32, 43, length + 1, 16, elementWidth, elementHeight);
    }

    /**
     * Renders a vertical progress arrow.
     *  @param x       The starting x position of the arrow.
     * @param y       The starting y position (bottom) of the arrow.
     */
    public void renderVerticalArrow(PoseStack poseStack, int x, int y) {
        setElementTex();
        int progress = container.getProgress();
        int processTime = container.getProcessTime();
        int length = processTime > 0 && progress > 0 ? progress * 30 / processTime : 0;
        blit(poseStack, this.leftPos + x, this.topPos + y - length, 39, 30 - length, 16, length + 1, elementWidth, elementHeight);
    }

    /**
     * Renders an energy buffer with the stored energy at the given coordinates.
     *  @param x             The starting x position of the buffer.
     * @param y             The starting y position (bottom) of the buffer.
     */
    public void renderEnergyBuffer(PoseStack pPoseStack, int x, int y) {
        setElementTex();
        int z = 60 - (60 * MathHelper.getPercent(container.getEnergy(), container.getMaxEnergy()) / 100);
        blit(pPoseStack, this.leftPos + x, this.topPos + y + z, 0, 0, 16, 60 - z, elementWidth, elementHeight);
        blit(pPoseStack, this.leftPos + x, this.topPos + y, 16, 0, 16, 60, elementWidth, elementHeight);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        String title = new TranslatableComponent(getKey()).getString();
        this.font.draw(pPoseStack, title, ((float) (imageWidth / 2 - font.width(title) / 2)) + 5, 6, 4210752);

        String inv = new TranslatableComponent("key.categories.inventory").getString();
        this.font.draw(pPoseStack, inv, ((float) (imageWidth / 2 - font.width(inv) / 2)) - 30, 74, 4210752);
    }

    protected void setElementTex() {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, new ResourceLocation(CLib.MODID, "textures/gui/screen_elements.png"));
    }
}
