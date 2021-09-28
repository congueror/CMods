package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MoonSkyRenderer {
    public static void render() {
        DimensionSpecialEffects.EFFECTS.put(new ResourceLocation(CGalaxy.MODID, "moon"),
                // cloudHeight, alternate sky color, fog type, render sky, diffuse lighting
                new DimensionSpecialEffects(Float.NaN, false, DimensionSpecialEffects.SkyType.NONE, false, false) {
                    @Override
                    public boolean isFoggyAt(int i, int i1) {//enable fog
                        return false;
                    }

                    @Nullable
                    @Override
                    public float[] getSunriseColor(float p_230492_1_, float p_230492_2_) {
                        return null;
                    }

                    @Nonnull
                    @Override
                    public Vec3 getBrightnessDependentFogColor(@Nonnull Vec3 p_108878_, float p_108879_) {
                        return p_108878_;
                    }

                    @Override
                    public ICloudRenderHandler getCloudRenderHandler() {
                        return (i, v, matrixStack, clientWorld, minecraft, v1, v2, v3) -> {

                        };
                    }

                    @Override
                    public ISkyRenderHandler getSkyRenderHandler() {
                        return (ticks, partialTicks, poseStack, level, mc) -> {
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
                            assert shaderinstance != null;
                            assert mc.levelRenderer.skyBuffer != null;
                            mc.levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), poseStack.last().pose(), shaderinstance);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();

                            //Sun
                            RenderSystem.enableTexture();
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            poseStack.pushPose();
                            poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                            poseStack.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(partialTicks) * 360.0F));
                            Matrix4f matrix4f1 = poseStack.last().pose();
                            float f12 = 30.0F;
                            RenderSystem.setShader(GameRenderer::getPositionTexShader);
                            RenderSystem.setShaderTexture(0, SUN);
                            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                            bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                            bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                            bufferbuilder.end();
                            BufferUploader.end(bufferbuilder);
                            poseStack.popPose();

                            //Earth
                            poseStack.pushPose();
                            poseStack.mulPose(Vector3f.YP.rotationDegrees(-100.0F));
                            poseStack.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(partialTicks) * 360.0F + 45.0F));
                            poseStack.mulPose(Vector3f.ZP.rotationDegrees(30.0F));
                            Matrix4f matrix4f2 = poseStack.last().pose();
                            f12 = 15.0F;
                            RenderSystem.setShaderTexture(0, EARTH);
                            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                            bufferbuilder.vertex(matrix4f2, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                            bufferbuilder.end();
                            BufferUploader.end(bufferbuilder);
                            poseStack.popPose();

                            //Stars
                            RenderSystem.disableTexture();
                            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                            FogRenderer.setupNoFog();
                            assert GameRenderer.getPositionShader() != null;
                            assert mc.levelRenderer.starBuffer != null;
                            mc.levelRenderer.starBuffer.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionShader());

                            RenderSystem.disableBlend();
                            if (level.effects().hasGround()) {
                                RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0f);
                            } else {
                                RenderSystem.setShaderColor(f, f1, f2, 1.0f);
                            }
                            RenderSystem.enableTexture();
                            RenderSystem.depthMask(true);

                            /*

                            mc.levelRenderer.skyBuffer.bind();
                            mc.levelRenderer.skyFormat.setupBufferState(0L);
                            mc.levelRenderer.skyBuffer.draw(poseStack.last().pose(), 7);
                            VertexBuffer.unbind();
                            mc.levelRenderer.skyFormat.clearBufferState();
                            RenderSystem.disableFog();
                            RenderSystem.disableAlphaTest();
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();

                            RenderSystem.enableTexture();
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                            poseStack.pushPose();

                            //Sun
                            poseStack.pushPose();
                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                            poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                            poseStack.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(partialTicks) * 360.0F));
                            Matrix4f matrix4f1 = poseStack.last().pose();
                            float f12 = 30.0F;
                            mc.getTextureManager().bind(SUN);
                            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                            bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                            bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                            bufferbuilder.end();
                            WorldVertexBufferUploader.end(bufferbuilder);
                            poseStack.popPose();

                            //Earth
                            poseStack.pushPose();
                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                            poseStack.mulPose(Vector3f.YP.rotationDegrees(-100.0F));
                            poseStack.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(partialTicks) * 360.0F + 45.0F));
                            poseStack.mulPose(Vector3f.ZP.rotationDegrees(30.0F));
                            Matrix4f matrix4f2 = poseStack.last().pose();
                            f12 = 15.0F;
                            mc.getTextureManager().bind(EARTH);
                            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                            bufferbuilder.vertex(matrix4f2, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                            bufferbuilder.end();
                            WorldVertexBufferUploader.end(bufferbuilder);
                            poseStack.popPose();

                            RenderSystem.disableTexture();

                            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                            //noinspection ConstantConditions
                            mc.levelRenderer.starBuffer.bind();
                            mc.levelRenderer.skyFormat.setupBufferState(0L);
                            mc.levelRenderer.starBuffer.draw(poseStack.last().pose(), 7);
                            VertexBuffer.unbind();
                            mc.levelRenderer.skyFormat.clearBufferState();


                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.disableBlend();
                            RenderSystem.enableAlphaTest();
                            RenderSystem.enableFog();
                            poseStack.popPose();
                            if (level.effects().hasGround()) {
                                RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
                            } else {
                                RenderSystem.color3f(f, f1, f2);
                            }
                            RenderSystem.enableTexture();
                            RenderSystem.depthMask(true);
                            RenderSystem.disableFog();*/
                        };
                    }
                });
    }
}
