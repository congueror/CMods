package net.congueror.cgalaxy.client.effects;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ISkyRenderHandler;
import org.jetbrains.annotations.Nullable;

public class MarsSkyEffects extends AbstractEffects {

    public MarsSkyEffects() {
        super(CGalaxy.location("mars_effect"), Float.NaN, false, SkyType.NONE, false, false);
    }

    @Override
    public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
        return super.getSunriseColor(pTimeOfDay, pPartialTicks);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 pFogColor, float pBrightness) {
        return pFogColor;
    }

    @Nullable
    @Override
    public ISkyRenderHandler getSkyRenderHandler() {
        return new AbstractSkyRenderer() {
            @Override
            public void render(int ticks, float partialTick, PoseStack poseStack, ClientLevel level, Minecraft mc) {
                ResourceLocation SUN = new ResourceLocation(CGalaxy.MODID, "textures/sky/sun_2.png");
                ResourceLocation PHOBOS = CGalaxy.location("texures/sky/phobos.png");
                ResourceLocation DEIMOS = CGalaxy.location("textures/sky/deimos.png");

                RenderSystem.disableTexture();
                Vec3 vec3 = level.getSkyColor(mc.gameRenderer.getMainCamera().getPosition(), partialTick);
                float f1 = (float) vec3.x;
                float f11 = (float) vec3.y;
                float f12 = (float) vec3.z;
                FogRenderer.levelFogColor();
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                RenderSystem.depthMask(false);
                RenderSystem.setShaderColor(f1, f11, f12, 1.0F);
                ShaderInstance shaderinstance = RenderSystem.getShader();
                assert mc.levelRenderer.skyBuffer != null;
                assert shaderinstance != null;
                mc.levelRenderer.skyBuffer.bind();
                mc.levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), poseStack.last().pose(), shaderinstance);
                VertexBuffer.unbind();
                DefaultVertexFormat.POSITION.clearBufferState();

                //Stars
                RenderSystem.defaultBlendFunc();
                float brightness = level.getStarBrightness(partialTick);
                if (brightness > 0) {
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    FogRenderer.levelFogColor();
                    poseStack.pushPose();
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 0.2f * 360.0F));
                    this.starVBO.bind();
                    this.starVBO.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), shaderinstance);
                    VertexBuffer.unbind();
                    poseStack.popPose();
                    DefaultVertexFormat.POSITION.clearBufferState();
                }

                float size; //Size of the rectangle

                //Sun
                RenderSystem.enableTexture();
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.pushPose();
                poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));
                Matrix4f matrix4f1 = poseStack.last().pose();
                size = 30.0F;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SUN);
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f1, -size, 100.0F, -size).uv(0.0F, 0.0F).endVertex();
                bufferbuilder.vertex(matrix4f1, size, 100.0F, -size).uv(1.0F, 0.0F).endVertex();
                bufferbuilder.vertex(matrix4f1, size, 100.0F, size).uv(1.0F, 1.0F).endVertex();
                bufferbuilder.vertex(matrix4f1, -size, 100.0F, size).uv(0.0F, 1.0F).endVertex();
                bufferbuilder.end();
                BufferUploader.end(bufferbuilder);
                poseStack.popPose();
                RenderSystem.disableBlend();

                RenderSystem.disableTexture();
                RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                double d0 = mc.player.getEyePosition(partialTick).y - level.getLevelData().getHorizonHeight(level);
                if (d0 < 0.0D) {
                    poseStack.pushPose();
                    poseStack.translate(0.0D, 12.0D, 0.0D);
                    mc.levelRenderer.darkBuffer.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), shaderinstance);
                    poseStack.popPose();
                }


                RenderSystem.disableBlend();
                if (level.effects().hasGround()) {
                    RenderSystem.setShaderColor(f1 * 0.2F + 0.04F, f11 * 0.2F + 0.04F, f12 * 0.6F + 0.1F, 1.0f);
                } else {
                    RenderSystem.setShaderColor(f1, f11, f12, 1.0f);
                }
                RenderSystem.enableTexture();
                RenderSystem.depthMask(true);
            }
        };
    }
}
