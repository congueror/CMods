package net.congueror.cgalaxy.client.effects;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ICloudRenderHandler;

public abstract class AbstractEffects extends DimensionSpecialEffects {
    final ResourceLocation location;

    public AbstractEffects(ResourceLocation location, float pCloudLevel, boolean pHasGround, SkyType pSkyType, boolean pForceBrightLightmap, boolean pConstantAmbientLight) {
        super(pCloudLevel, pHasGround, pSkyType, pForceBrightLightmap, pConstantAmbientLight);
        this.location = location;
    }

    public void build() {
        EFFECTS.put(location, this);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 pFogColor, float pBrightness) {
        return Vec3.ZERO;
    }

    @Override
    public boolean isFoggyAt(int pX, int pY) {
        return false;
    }

    @Override
    public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
        return null;
    }

    @Override
    public ICloudRenderHandler getCloudRenderHandler() {
        return (i, v, matrixStack, clientWorld, minecraft, v1, v2, v3) -> {

        };
    }
}
