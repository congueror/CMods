package net.congueror.clib.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.congueror.clib.CLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

import static net.minecraft.util.Mth.TWO_PI;

public class RenderingHelper {
    /**
     * Renders an entity on the screen.
     *
     * @param pLivingEntity Entity to be rendered
     * @param rotation      Rotation in degrees.
     */
    public static void renderEntityInInventoryWithRotations(int pPosX, int pPosY, int pScale, float pMouseX, float pMouseY, LivingEntity pLivingEntity, float rotation) {
        float f = (float) Math.atan(pMouseX / 40.0F);
        float f1 = (float) Math.atan(pMouseY / 40.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(pPosX, pPosY, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float) pScale, (float) pScale, (float) pScale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);

        posestack1.mulPose(Vector3f.YP.rotationDegrees(rotation));

        float f2 = pLivingEntity.yBodyRot;
        float f3 = pLivingEntity.getYRot();
        float f4 = pLivingEntity.getXRot();
        float f5 = pLivingEntity.yHeadRotO;
        float f6 = pLivingEntity.yHeadRot;
        pLivingEntity.yBodyRot = 180.0F + f * 20.0F;
        pLivingEntity.setYRot(180.0F + f * 40.0F);
        pLivingEntity.setXRot(-f1 * 20.0F);
        pLivingEntity.yHeadRot = pLivingEntity.getYRot();
        pLivingEntity.yHeadRotO = pLivingEntity.getYRot();

        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> entityrenderdispatcher.render(pLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880));
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        pLivingEntity.yBodyRot = f2;
        pLivingEntity.setYRot(f3);
        pLivingEntity.setXRot(f4);
        pLivingEntity.yHeadRotO = f5;
        pLivingEntity.yHeadRot = f6;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    /**
     * Renders a fluid on the screen
     */
    public static void renderFluid(Matrix4f matrix, FluidStack fluid, int x1, int x2, int y1, int y2, int blitOffset) {
        int color = fluid.getFluid().getAttributes().getColor();
        float r = MathHelper.hexARGBtoRGBA(color).x();
        float g = MathHelper.hexARGBtoRGBA(color).y();
        float b = MathHelper.hexARGBtoRGBA(color).z();
        ResourceLocation texture = fluid.getFluid().getAttributes().getStillTexture();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.setShaderColor(r, g, b, 1.0f);
        RenderSystem.enableBlend();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix, (float) x1, (float) y2, (float) blitOffset).uv(sprite.getU0(), sprite.getV1()).endVertex();
        bufferbuilder.vertex(matrix, (float) x2, (float) y2, (float) blitOffset).uv(sprite.getU1(), sprite.getV1()).endVertex();
        bufferbuilder.vertex(matrix, (float) x2, (float) y1, (float) blitOffset).uv(sprite.getU1(), sprite.getV0()).endVertex();
        bufferbuilder.vertex(matrix, (float) x1, (float) y1, (float) blitOffset).uv(sprite.getU0(), sprite.getV0()).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    /**
     * Checks whether it is day time. An improvement of {@link Level#isDay()} because it works in both client and server.
     *
     * @return True if day time, false if night.
     */
    public static boolean isDayTime(Level level) {
        double d2 = 0.5D + 2.0D * Mth.clamp(Mth.cos(level.getTimeOfDay(1.0F) * ((float) Math.PI * 2F)), -0.25D, 0.25D);
        int darkness = (int) ((1.0D - d2) * 11.0D);
        return darkness < 4;
    }

    /**
     * Renders a tooltip with a custom color and message.
     *
     * @param mes    The text to be displayed on the tooltip.
     * @param color1 Background color. in argb hex format.
     * @param color2 Frame color. in argb hex format.
     */
    public static void renderTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, Component mes, int color1, int color2) {
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        int i = mc.font.width(mes);
        int j = 8;

        int j2 = pMouseX + 12;
        int k2 = pMouseY - 12;
        if (j2 + i > width) {
            j2 -= 28 + i;
        }

        if (k2 + j + 6 > height) {
            k2 = height - j - 6;
        }

        pPoseStack.pushPose();
        mc.getItemRenderer().blitOffset = 400.0F;
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix4f = pPoseStack.last().pose();
        fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 4, j2 + i + 3, k2 - 3, 400, color1, color1);
        fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 + j + 3, j2 + i + 3, k2 + j + 4, 400, color1, color1);
        fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 + j + 3, 400, color1, color1);
        fillGradient(matrix4f, bufferbuilder, j2 - 4, k2 - 3, j2 - 3, k2 + j + 3, 400, color1, color1);
        fillGradient(matrix4f, bufferbuilder, j2 + i + 3, k2 - 3, j2 + i + 4, k2 + j + 3, 400, color1, color1);
        fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + j + 3 - 1, 400, color2, color1);
        fillGradient(matrix4f, bufferbuilder, j2 + i + 2, k2 - 3 + 1, j2 + i + 3, k2 + j + 3 - 1, 400, color2, color1);
        fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 - 3 + 1, 400, color2, color2);
        fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 + j + 2, j2 + i + 3, k2 + j + 3, 400, color1, color1);
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        pPoseStack.translate(0.0D, 0.0D, 400.0D);

        int l1 = k2;
        mc.font.drawInBatch(mes, j2, l1, -1, true, pPoseStack.last().pose(), multibuffersource$buffersource, false, 0, 15728880);
        RenderSystem.enableBlend();
        multibuffersource$buffersource.endBatch();
        pPoseStack.popPose();
    }

    /**
     * Fork of {@link net.minecraft.client.gui.GuiComponent}#fillGradient. (a protected method)
     */
    public static void fillGradient(Matrix4f pMatrix, BufferBuilder pBuilder, int pX1, int pY1, int pX2, int pY2, int pBlitOffset, int pColorA, int pColorB) {
        float f = (float) (pColorA >> 24 & 255) / 255.0F;
        float f1 = (float) (pColorA >> 16 & 255) / 255.0F;
        float f2 = (float) (pColorA >> 8 & 255) / 255.0F;
        float f3 = (float) (pColorA & 255) / 255.0F;
        float f4 = (float) (pColorB >> 24 & 255) / 255.0F;
        float f5 = (float) (pColorB >> 16 & 255) / 255.0F;
        float f6 = (float) (pColorB >> 8 & 255) / 255.0F;
        float f7 = (float) (pColorB & 255) / 255.0F;
        pBuilder.vertex(pMatrix, (float) pX2, (float) pY1, (float) pBlitOffset).color(f1, f2, f3, f).endVertex();
        pBuilder.vertex(pMatrix, (float) pX1, (float) pY1, (float) pBlitOffset).color(f1, f2, f3, f).endVertex();
        pBuilder.vertex(pMatrix, (float) pX1, (float) pY2, (float) pBlitOffset).color(f5, f6, f7, f4).endVertex();
        pBuilder.vertex(pMatrix, (float) pX2, (float) pY2, (float) pBlitOffset).color(f5, f6, f7, f4).endVertex();
    }

    /**
     * Draws a hollow ellipse on the screen.
     *
     * @param radiusIn      Radius of the inner circle.
     * @param radiusOut     Radius of the outer circle.
     * @param colorOut      An array of integers representing rgba for the outer ring.
     * @param colorIn       An array of integers representing rgba for the inner ring.
     * @param xEccentricity The eccentricity of the ellipse on the x-axis, default being 1.0f.(Note: May not work correctly if both x and y eccentricities are non-1 values)
     * @param yEccentricity The eccentricity of the ellipse on the y-axis, default being 1.0f.(Note: May not work correctly if both x and y eccentricities are non-1 values)
     */
    public static void drawEllipse(BufferBuilder buffer, int x, int y, float radiusIn, float radiusOut, int[] colorOut, int[] colorIn, float xEccentricity, float yEccentricity) {
        float endAngle = (float) (0.75 * TWO_PI + Math.PI);
        float startAngle = (float) (-0.25 * TWO_PI + Math.PI);
        float angle = endAngle - startAngle;
        int sections = Math.max(1, Mth.ceil(angle / (2.5f / 360.0f)));
        float slice = angle / sections;

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + i * slice;
            float angle2 = startAngle + (i + 1) * slice;

            float pos1InX = x + radiusIn * (float) Math.cos(angle1) * xEccentricity;
            float pos1InY = y + radiusIn * (float) Math.sin(angle1) * yEccentricity;
            float pos2InX = x + radiusIn * (float) Math.cos(angle2) * xEccentricity;
            float pos2InY = y + radiusIn * (float) Math.sin(angle2) * yEccentricity;

            float pos1OutX = x + radiusOut * (float) Math.cos(angle1) * xEccentricity;
            float pos1OutY = y + radiusOut * (float) Math.sin(angle1) * yEccentricity;
            float pos2OutX = x + radiusOut * (float) Math.cos(angle2) * xEccentricity;
            float pos2OutY = y + radiusOut * (float) Math.sin(angle2) * yEccentricity;

            buffer.vertex(pos1OutX, pos1OutY, 0).color(colorOut[0], colorOut[1], colorOut[2], colorOut[3]).endVertex();
            buffer.vertex(pos1InX, pos1InY, 0).color(colorIn[0], colorIn[1], colorIn[2], colorIn[3]).endVertex();
            buffer.vertex(pos2InX, pos2InY, 0).color(colorIn[0], colorIn[1], colorIn[2], colorIn[3]).endVertex();
            buffer.vertex(pos2OutX, pos2OutY, 0).color(colorOut[0], colorOut[1], colorOut[2], colorOut[3]).endVertex();
        }
    }

    /**
     * Fork of {@link net.minecraft.client.renderer.LevelRenderer}#drawStars(BufferBuilder) that allows the amount of stars to be changed.
     *
     * @param bufferBuilder A buffer builder.
     * @param stars         Number of stars in the sky.
     */
    public static void renderStars(BufferBuilder bufferBuilder, int stars) {
        Random random = new Random(10842L);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int i = 0; i < stars; ++i) {
            double d0 = random.nextFloat() * 2.0F - 1.0F;
            double d1 = random.nextFloat() * 2.0F - 1.0F;
            double d2 = random.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.15F + random.nextFloat() * 0.1F;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d0 = d0 * d4;
                d1 = d1 * d4;
                d2 = d2 * d4;
                double d5 = d0 * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for (int j = 0; j < 4; ++j) {
                    double d17 = ((j & 2) - 1) * d3;
                    double d18 = ((j + 1 & 2) - 1) * d3;
                    double d19 = d17 * d16 - d18 * d15;
                    double d20 = d18 * d16 + d17 * d15;
                    double d21 = d19 * d12 + 0.0D * d13;
                    double d22 = 0.0D * d12 - d19 * d13;
                    double d23 = d22 * d9 - d20 * d10;
                    double d24 = d20 * d9 + d22 * d10;

                    double x = d5 + d23;
                    double y = d6 + d21;
                    double z = d7 + d24;
                    bufferBuilder.vertex(x, y, z).endVertex();
                }
            }
        }
    }
}
