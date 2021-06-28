package com.congueror.cgalaxy.util.events;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.clib.CLib;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            //RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ROCKET.get(), RocketRenderer::new);

            DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(CGalaxy.MODID, "moon"),
                    // cloudHeight, alternate sky color, fog type, render sky, diffuse lighting
                    new DimensionRenderInfo(99999999, false, DimensionRenderInfo.FogType.NORMAL, false, false) {
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
                        public ICloudRenderHandler getCloudRenderHandler() {
                            return (i, v, matrixStack, clientWorld, minecraft, v1, v2, v3) -> {

                            };
                        }

                        @Nullable
                        @Override
                        public ISkyRenderHandler getSkyRenderHandler() {
                            return (ticks, partialTicks, matrixStack, world, mc) -> {
                                RenderSystem.disableTexture();
                                Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
                                float x = (float) vector3d.x;
                                float y = (float) vector3d.y;
                                float z = (float) vector3d.z;
                                FogRenderer.applyFog();
                                BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
                                RenderSystem.depthMask(false);
                                RenderSystem.enableFog();
                                RenderSystem.color3f(x, y, z);

                                mc.worldRenderer.skyVBO.bindBuffer();
                                mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
                                mc.worldRenderer.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
                                VertexBuffer.unbindBuffer();
                                mc.worldRenderer.skyVertexFormat.clearBufferState();
                                Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                                RenderSystem.enableAlphaTest();
                                RenderSystem.enableTexture();
                                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                                RenderSystem.color4f(1f, 1f, 1f, 1f);
                                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/sky/moon_sky.png"));
                                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                                bufferbuilder.pos(matrix4f, -100, 8f, -100).tex(0.0F, 0.0F).endVertex();
                                bufferbuilder.pos(matrix4f, 100, 8f, -100).tex(1.0F, 0.0F).endVertex();
                                bufferbuilder.pos(matrix4f, 100, 8f, 100).tex(1.0F, 1.0F).endVertex();
                                bufferbuilder.pos(matrix4f, -100, 8f, 100).tex(0.0F, 1.0F).endVertex();
                                bufferbuilder.finishDrawing();
                                WorldVertexBufferUploader.draw(bufferbuilder);
                                RenderSystem.disableTexture();
                                RenderSystem.disableFog();
                                RenderSystem.disableAlphaTest();
                                RenderSystem.enableBlend();
                                RenderSystem.defaultBlendFunc();
                                float[] afloat = world.getDimensionRenderInfo().func_230492_a_(world.func_242415_f(partialTicks), partialTicks);
                                if (afloat != null) {
                                    RenderSystem.disableTexture();
                                    RenderSystem.shadeModel(7425);
                                    matrixStack.push();
                                    matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
                                    float f3 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F;
                                    matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3));
                                    matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
                                    float f4 = afloat[0];
                                    float f5 = afloat[1];
                                    float f6 = afloat[2];
                                    Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
                                    bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                                    bufferbuilder.pos(matrix4f1, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
                                    for (int j = 0; j <= 16; ++j) {
                                        float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
                                        float f8 = MathHelper.sin(f7);
                                        float f9 = MathHelper.cos(f7);
                                        bufferbuilder.pos(matrix4f1, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3])
                                                .color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
                                    }
                                    bufferbuilder.finishDrawing();
                                    WorldVertexBufferUploader.draw(bufferbuilder);
                                    matrixStack.pop();
                                    RenderSystem.shadeModel(7424);
                                }
                                RenderSystem.enableTexture();
                                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
                                        GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                                matrixStack.push();
                                float f11 = 100000.0F - world.getRainStrength(partialTicks);// Rrain basiss ist es auf
                                // 1.0F
                                RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
                                matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
                                matrixStack.rotate(Vector3f.XP.rotationDegrees(0.0F /* world.func_242415_f(partialTicks) * 360.0F */));
                                // Sun Rotation (Main Planet)
                                matrix4f = matrixStack.getLast().getMatrix();
                                float f12 = 30.0F;
                                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/sky/moon_sun.png"));
                                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

                                // new System (Sun main Planet)
                                float f17 = (float) mc.player.getEyePosition(partialTicks).y /*- world.getWorldInfo().getVoidFogHeight()*/;
                                bufferbuilder.pos(matrix4f, -35.0F, -f17 - 18.0F, 35.0F).tex(0.0F, 0.0F).endVertex(); // 350

                                bufferbuilder.pos(matrix4f, 35.0F, -f17 - 18.0F, 35.0F).tex(1.0F, 0.0F).endVertex();
                                bufferbuilder.pos(matrix4f, 35.0F, -f17 - 18.0F, -35.0F).tex(1.0F, 1.0F).endVertex();
                                bufferbuilder.pos(matrix4f, -35.0F, -f17 - 18.0F, -35.0F).tex(0.0F, 1.0F).endVertex();
                                bufferbuilder.finishDrawing();
                                WorldVertexBufferUploader.draw(bufferbuilder);
                                f12 = 20.0F;
                                matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));// Moon
                                // Rotation
                                // (Sun
                                // Planet)
                                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/sky/moon_moon.png"));
                                int k = world.getMoonPhase();
                                int l = k % 4;
                                int i1 = k / 4 % 2;
                                float f13 = (float) (l + 0) / 4.0F;
                                float f14 = (float) (i1 + 0) / 2.0F;
                                float f15 = (float) (l + 1) / 4.0F;
                                float f16 = (float) (i1 + 1) / 2.0F;
                                // New Orbit Planet System
                                // float f17 = (float) mc.player.getEyePosition(partialTicks).y /*-
                                // world.getWorldInfo().getVoidFogHeight()*/;
                                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                                // bufferbuilder.pos(matrix4f1, -f12, -100.0F, f12).tex(f15, f16).endVertex();
                                // bufferbuilder.pos(matrix4f1, f12, -100.0F, f12).tex(f13, f16).endVertex();
                                // bufferbuilder.pos(matrix4f1, f12, -100.0F, -f12).tex(f13, f14).endVertex();
                                // bufferbuilder.pos(matrix4f1, -f12, -100.0F, -f12).tex(f15, f14).endVertex();
                                // New System
                                /*
                                 * bufferbuilder.pos(matrix4f1, -300.0F, -f17 -18.0F, 300.0F).tex(f15,
                                 * f16).endVertex(); //350 is nice but fps xD bufferbuilder.pos(matrix4f1,
                                 * 300.0F, -f17 -18.0F, 300.0F).tex(f13, f16).endVertex();
                                 * bufferbuilder.pos(matrix4f1, 300.0F, -f17 -18.0F, -300.0F).tex(f13,
                                 * f14).endVertex(); bufferbuilder.pos(matrix4f1, -300.0F, -f17 -18.0F,
                                 * -300.0F).tex(f15, f14).endVertex();
                                 */
                                // moon Texture
                                bufferbuilder.pos(matrix4f, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
                                bufferbuilder.pos(matrix4f, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
                                bufferbuilder.pos(matrix4f, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
                                bufferbuilder.pos(matrix4f, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
                                bufferbuilder.finishDrawing();
                                WorldVertexBufferUploader.draw(bufferbuilder);
                                RenderSystem.disableTexture();
                                // f11 = 1000.0F;// Star Brightness
                                float f10 = 1.0F;// world.getStarBrightness(partialTicks) * f11;
                                // f11
                                if (f10 > 0.0F) {
                                    RenderSystem.color4f(f10, f10, f10, f10);
                                    mc.worldRenderer.starVBO.bindBuffer();
                                    mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
                                    mc.worldRenderer.starVBO.draw(matrixStack.getLast().getMatrix(), 7);
                                    VertexBuffer.unbindBuffer();
                                    mc.worldRenderer.skyVertexFormat.clearBufferState();
                                }
                                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                                RenderSystem.disableBlend();
                                RenderSystem.enableAlphaTest();
                                RenderSystem.enableFog();
                                matrixStack.pop();
                                RenderSystem.disableTexture();
                                RenderSystem.color3f(0.0F, 0.0F, 0.0F);
                                double d0 = 2.0F;// mc.player.getEyePosition(partialTicks).y -
                                // world.getWorldInfo().getVoidFogHeight();
                                // This is the Player High When The Sky Removed zb. bei 60 blöcken
                                if (d0 < 1.0D) {
                                    // 0.0D
                                    matrixStack.push();
                                    matrixStack.translate(0.0D, 12.0D, 0.0D);
                                    mc.worldRenderer.sky2VBO.bindBuffer();
                                    mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
                                    mc.worldRenderer.sky2VBO.draw(matrixStack.getLast().getMatrix(), 7);
                                    VertexBuffer.unbindBuffer();
                                    mc.worldRenderer.skyVertexFormat.clearBufferState();
                                    matrixStack.pop();
                                }
                                if (world.getDimensionRenderInfo().func_239216_b_()) {
                                    RenderSystem.color3f(x * 0.2F + 0.04F, y * 0.2F + 0.04F, z * 0.6F + 0.1F);
                                } else {
                                    RenderSystem.color3f(x, y, z);
                                }
                                RenderSystem.enableTexture();
                                RenderSystem.depthMask(true);
                                RenderSystem.disableFog();
                            };
                        }
                    });
        }
    }
    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {

    }
}
