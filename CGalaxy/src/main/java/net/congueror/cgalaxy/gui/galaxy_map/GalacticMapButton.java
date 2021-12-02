package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class GalacticMapButton extends Button {
    private final Type type;
    private final GalacticObjectBuilder.GalacticObject<?> object;
    private ResourceLocation resourceLocation;
    private int yTexStart;
    private final int yDiffTex;
    private final int xTexStart;
    private int textureWidth;
    private int textureHeight;
    private OnTooltip onTooltip = NO_TOOLTIP;

    public GalacticMapButton(Type type, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, OnPress pOnPress, GalacticObjectBuilder.GalacticObject<?> object) {
        super(pX, pY, pWidth, pHeight, TextComponent.EMPTY, pOnPress, NO_TOOLTIP);
        this.type = type;
        this.object = object;
        this.textureWidth = pTextureWidth;
        this.textureHeight = pTextureHeight;
        this.xTexStart = pXTexStart;
        this.yTexStart = pYTexStart;
        this.yDiffTex = pYDiffTex;
        this.resourceLocation = pResourceLocation;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public void setYTexStart(int yTexStart) {
        this.yTexStart = yTexStart;
    }

    public void setOnTooltip(OnTooltip onTooltip) {
        this.onTooltip = onTooltip;
    }

    @Override
    public void renderToolTip(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(this, pMatrixStack, pMouseX, pMouseY);
    }

    public void renderButton(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int i = this.yTexStart;
        if (this.isHovered()) {
            i += this.yDiffTex;
        }

        RenderSystem.enableDepthTest();
        blit(pMatrixStack, this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.textureWidth, this.textureHeight);
        if (this.isHovered()) {
            this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
        }
    }

    public Type getType() {
        return type;
    }

    public GalacticObjectBuilder.GalacticObject<?> getObject() {
        return object;
    }

    enum Type {
        ORBITER,
        LAUNCH,
        ORBITEE
    }
}
