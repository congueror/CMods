package com.congueror.cgalaxy.client.sky_renderers;

import com.congueror.cgalaxy.CGalaxy;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;

public class MoonSkyRenderer {
    public static void render() {
        DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(CGalaxy.MODID, "moon"),
                // cloudHeight, alternate sky color, fog type, render sky, diffuse lighting
                new DimensionRenderInfo(Float.NaN, false, DimensionRenderInfo.FogType.NONE, false, false) {
                    @Override
                    public Vector3d func_230494_a_(Vector3d vector3d, float v) {//adjust sky color
                        return vector3d;
                    }

                    @Override
                    public boolean func_230493_a_(int i, int i1) {//enable fog
                        return false;
                    }

                    @Nullable
                    @Override
                    public float[] func_230492_a_(float p_230492_1_, float p_230492_2_) {
                        return null;
                    }

                    @Override
                    public ICloudRenderHandler getCloudRenderHandler() {
                        return (i, v, matrixStack, clientWorld, minecraft, v1, v2, v3) -> {

                        };
                    }

                    @Override
                    public ISkyRenderHandler getSkyRenderHandler() {
                        return (ticks, partialTicks, matrixStack, world, mc) -> {
                            ResourceLocation SUN = new ResourceLocation(CGalaxy.MODID, "textures/sky/moon_sun.png");
                            ResourceLocation EARTH = new ResourceLocation(CGalaxy.MODID, "textures/sky/earth.png");
                            //Forked from WorldRenderer.renderSky
                            RenderSystem.disableTexture();
                            Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
                            float f = (float) vector3d.x;
                            float f1 = (float) vector3d.y;
                            float f2 = (float) vector3d.z;
                            FogRenderer.applyFog();
                            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
                            RenderSystem.depthMask(false);
                            RenderSystem.enableFog();
                            RenderSystem.color3f(f, f1, f2);
                            //noinspection ConstantConditions
                            mc.worldRenderer.skyVBO.bindBuffer();
                            mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
                            mc.worldRenderer.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
                            VertexBuffer.unbindBuffer();
                            mc.worldRenderer.skyVertexFormat.clearBufferState();
                            RenderSystem.disableFog();
                            RenderSystem.disableAlphaTest();
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();

                            RenderSystem.enableTexture();
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                            matrixStack.push();

                            //Sun
                            matrixStack.push();
                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
                            matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));
                            Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
                            float f12 = 30.0F;
                            mc.getTextureManager().bindTexture(SUN);
                            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                            bufferbuilder.pos(matrix4f1, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
                            bufferbuilder.pos(matrix4f1, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
                            bufferbuilder.pos(matrix4f1, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
                            bufferbuilder.pos(matrix4f1, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
                            bufferbuilder.finishDrawing();
                            WorldVertexBufferUploader.draw(bufferbuilder);
                            matrixStack.pop();

                            //Earth
                            matrixStack.push();
                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                            matrixStack.rotate(Vector3f.YP.rotationDegrees(-100.0F));
                            matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F + 45.0F));
                            matrixStack.rotate(Vector3f.ZP.rotationDegrees(30.0F));
                            Matrix4f matrix4f2 = matrixStack.getLast().getMatrix();
                            f12 = 15.0F;
                            mc.getTextureManager().bindTexture(EARTH);
                            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                            bufferbuilder.pos(matrix4f2, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
                            bufferbuilder.pos(matrix4f2, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
                            bufferbuilder.pos(matrix4f2, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
                            bufferbuilder.pos(matrix4f2, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
                            bufferbuilder.finishDrawing();
                            WorldVertexBufferUploader.draw(bufferbuilder);
                            matrixStack.pop();

                            RenderSystem.disableTexture();

                            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                            //noinspection ConstantConditions
                            mc.worldRenderer.starVBO.bindBuffer();
                            mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
                            mc.worldRenderer.starVBO.draw(matrixStack.getLast().getMatrix(), 7);
                            VertexBuffer.unbindBuffer();
                            mc.worldRenderer.skyVertexFormat.clearBufferState();


                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.disableBlend();
                            RenderSystem.enableAlphaTest();
                            RenderSystem.enableFog();
                            matrixStack.pop();
                            if (world.getDimensionRenderInfo().func_239216_b_()) {
                                RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
                            } else {
                                RenderSystem.color3f(f, f1, f2);
                            }
                            RenderSystem.enableTexture();
                            RenderSystem.depthMask(true);
                            RenderSystem.disableFog();
                        };
                    }
                });
    }
}
