package net.congueror.cgalaxy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MutableImageButton extends Button {

    int xTexStart;
    int yTexStart;
    int yOffset;
    ResourceLocation texture;
    int textureWidth;
    int textureHeight;
    Button.OnPress onPress;
    Button.OnTooltip onTooltip;

    public MutableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.OnPress pOnPress) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, NO_TOOLTIP);
    }

    public MutableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.OnPress pOnPress, Button.OnTooltip onTooltip) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, TextComponent.EMPTY, pOnPress, onTooltip);
    }

    public MutableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Component text, Button.OnPress pOnPress) {
        this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, text, pOnPress, NO_TOOLTIP);
    }

    public MutableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Component text, Button.OnPress pOnPress, Button.OnTooltip onTooltip) {
        super(pX, pY, pWidth, pHeight, text, pButton -> {});
        this.xTexStart = pXTexStart;
        this.yTexStart = pYTexStart;
        this.yOffset = pYDiffTex;
        this.texture = pResourceLocation;
        this.textureWidth = pTextureWidth;
        this.textureHeight = pTextureHeight;
        this.onPress = pOnPress;
        this.onTooltip = onTooltip;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setxTexStart(int xTexStart) {
        this.xTexStart = xTexStart;
    }

    public void setyTexStart(int yTexStart) {
        this.yTexStart = yTexStart;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public void setOnPress(OnPress onPress) {
        this.onPress = onPress;
    }

    public void setOnTooltip(OnTooltip onTooltip) {
        this.onTooltip = onTooltip;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(this, pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        int i = this.yTexStart;
        if (this.isHoveredOrFocused()) {
            i += this.yOffset;
        }

        RenderSystem.enableDepthTest();
        blit(pPoseStack, this.x, this.y, (float) this.xTexStart, (float) i, this.width, this.height, this.textureWidth, this.textureHeight);
        if (this.isHovered) {
            this.renderToolTip(pPoseStack, pMouseX, pMouseY);
        }

        if (!getMessage().equals(TextComponent.EMPTY)) {
            drawCenteredString(pPoseStack, Minecraft.getInstance().font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }

    public int getxTexStart() {
        return xTexStart;
    }

    public int getyTexStart() {
        return yTexStart;
    }

    public int getyOffset() {
        return yOffset;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public OnPress getOnPress() {
        return onPress;
    }

    public OnTooltip getOnTooltip() {
        return onTooltip;
    }
}
