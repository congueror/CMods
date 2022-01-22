package net.congueror.clib.blocks.abstract_machine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.clib.CLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractSettingsScreen extends Screen {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CLib.MODID, "textures/gui/settings_gui.png");
    protected final int NUM_OF_PAGES;

    protected int leftPos = (this.width - 110) / 2;
    protected int topPos = (this.height - 140) / 2;

    protected AbstractSettingsScreen(Component pTitle, int numOfPages) {
        super(pTitle);
        NUM_OF_PAGES = numOfPages;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - 110) / 2;
        topPos = (this.height - 140) / 2;
        addRenderableWidget(new ImageButton(this.leftPos + 99, this.topPos + 4, 7, 7, 0, 0, 0, new ResourceLocation(CLib.MODID, "textures/gui/blank.png"), width, height, pButton -> {
            Minecraft.getInstance().popGuiLayer();
        }));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderBg(pPoseStack);
    }

    protected void renderBg(PoseStack poseStack) {
        this.renderBackground(poseStack);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, 110, 140, 115, 140);
    }
}
