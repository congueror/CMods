package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.gui.MutableImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapScreen.startRadius;
import static net.minecraft.client.gui.screens.Screen.hasShiftDown;

public class GalacticMapButton extends MutableImageButton {
    private final Type type;
    private final GalacticObjectBuilder.GalacticObject<?> object;

    private int i, angle;
    private double zoom;
    private int moveX, moveY;
    public int xSel, ySel, sizeSel;

    public GalacticMapButton(Type type, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, OnPress pOnPress, GalacticObjectBuilder.GalacticObject<?> object) {
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress);
        this.type = type;
        this.object = object;

        angle = new Random().nextInt(361);
    }

    public void updateOrbiter(GalacticObjectBuilder.GalacticObject<?> obj, int width, int height, int size, double zoom, int moveX, int moveY, Button.OnTooltip onTooltip) {
        if (type.equals(Type.ORBITER)) {
            setWidth(size);
            setHeight(size);
            setTextureWidth(size);
            setTextureHeight(size);
            setOnTooltip(onTooltip);

            if (!hasShiftDown()) {
                i++;
            }
            this.zoom = zoom;
            this.moveX = moveX;
            this.moveY = moveY;
            int period = (int) ((obj.getDaysPerYear() * 5));
            if (i >= period / 20) {
                i = 0;
                angle++;
            }
            if (angle == 360) {
                angle = 0;
            }
            double r1 = startRadius + (49 * obj.getRingIndex());
            double r2 = 0.6 * (startRadius + (49 * obj.getRingIndex()));
            x = (int) ((width / 2 + 56) + zoom * r1 * Math.cos(Math.toRadians(angle)) - (size / 2) + moveX);
            y = (int) ((height / 2 + 9) + zoom * r2 * Math.sin(Math.toRadians(angle)) - (size / 2) + moveY);

            if (obj.getType() instanceof GalacticObjectBuilder.Moon) {
                sizeSel = (int) (18 * zoom);
                xSel = (int) ((width / 2 + 56) + zoom * r1 * Math.cos(Math.toRadians(angle)) - (sizeSel / 2) + moveX);
                ySel = (int) ((height / 2 + 9) + zoom * r2 * Math.sin(Math.toRadians(angle)) - (sizeSel / 2) + moveY);
            } else {
                xSel = -1;
                ySel = -1;
            }
        }
    }

    @Override
    public void renderToolTip(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY) {
        this.onTooltip.onTooltip(this, pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    public void renderButton(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.texture);
        int i = this.yTexStart;
        if (this.isHoveredOrFocused()) {
            i += this.yOffset;
        }

        RenderSystem.enableDepthTest();
        blit(pMatrixStack, this.x, this.y, (float) this.xTexStart, (float) i, this.width, this.height, this.textureWidth, this.textureHeight);

        if (this.isHoveredOrFocused()) {
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
        ORBITEE,
        SPACE_STATION
    }
}
