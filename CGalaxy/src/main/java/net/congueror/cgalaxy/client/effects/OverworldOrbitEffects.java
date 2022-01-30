package net.congueror.cgalaxy.client.effects;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ISkyRenderHandler;

public class OverworldOrbitEffects extends AbstractEffects {
    public OverworldOrbitEffects() {
        super(CGalaxy.location("overworld_orbit"), Float.NaN, false, DimensionSpecialEffects.SkyType.NONE, false, false);
    }

    @Override
    public ISkyRenderHandler getSkyRenderHandler() {
        return new AbstractSkyRenderer() {
            @Override
            public void render(int ticks, float partialTicks, PoseStack poseStack, ClientLevel level, Minecraft mc) {
                ResourceLocation SUN = new ResourceLocation(CGalaxy.MODID, "textures/sky/moon_sun.png");
                ResourceLocation EARTH = new ResourceLocation(CGalaxy.MODID, "textures/sky/earth.png");
                //Forked from WorldRenderer.renderSky
                RenderSystem.disableTexture();

                Vec3 vec3 = level.getSkyColor(mc.gameRenderer.getMainCamera().getPosition(), partialTicks);
                float f = (float) vec3.x;
                float f1 = (float) vec3.y;
                float f2 = (float) vec3.z;
                FogRenderer.levelFogColor();
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                RenderSystem.depthMask(false);
                RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                ShaderInstance shaderinstance = RenderSystem.getShader();
                assert mc.levelRenderer.skyBuffer != null;
                mc.levelRenderer.skyBuffer.bind();
                mc.levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), shaderinstance);
                VertexBuffer.unbind();
                DefaultVertexFormat.POSITION.clearBufferState();

                //Stars
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                this.starVBO.bind();
                this.starVBO.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), shaderinstance);
                VertexBuffer.unbind();
                DefaultVertexFormat.POSITION.clearBufferState();

                float size; //size of the rectangle.

                //Sun
                float test = level.getTimeOfDay(partialTicks);
                float xRot = level.getTimeOfDay(partialTicks) * 360.0F;
                if (xRot <= 120 || xRot >= 240) {
                    RenderSystem.enableTexture();
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.pushPose();
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(xRot));
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
                }

                //Earth
                size = 140.0F;
                RenderSystem.enableTexture();
                RenderSystem.disableDepthTest();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.pushPose();
                poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
                poseStack.translate(0, 0, 0);
                Matrix4f matrix4f2 = poseStack.last().pose();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, EARTH);
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f2, -size, 70.0F, -size).uv(0.0F, 0.0F).endVertex();
                bufferbuilder.vertex(matrix4f2, size, 70.0F, -size).uv(1.0F, 0.0F).endVertex();
                bufferbuilder.vertex(matrix4f2, size, 70.0F, size).uv(1.0F, 1.0F).endVertex();
                bufferbuilder.vertex(matrix4f2, -size, 70.0F, size).uv(0.0F, 1.0F).endVertex();
                bufferbuilder.end();
                BufferUploader.end(bufferbuilder);
                poseStack.popPose();
                RenderSystem.enableDepthTest();

                RenderSystem.disableBlend();
                if (level.effects().hasGround()) {
                    RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0f);
                } else {
                    RenderSystem.setShaderColor(f, f1, f2, 1.0f);
                }
                RenderSystem.enableTexture();
                RenderSystem.depthMask(true);
            }
        };
    }
}