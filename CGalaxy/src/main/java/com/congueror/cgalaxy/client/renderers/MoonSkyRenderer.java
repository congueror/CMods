package com.congueror.cgalaxy.client.renderers;

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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;

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

                    @Override
                    public ICloudRenderHandler getCloudRenderHandler() {
                        return (i, v, matrixStack, clientWorld, minecraft, v1, v2, v3) -> {

                        };
                    }

                    @Override
                    public ISkyRenderHandler getSkyRenderHandler() {
                        return (ticks, partialTicks, matrixStack, world, mc) -> {
                            //TODO: Rewrite

                        };
                    }
                });
    }
}
