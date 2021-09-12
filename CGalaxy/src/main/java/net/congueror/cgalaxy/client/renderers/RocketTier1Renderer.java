package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.RocketTier1Model;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class RocketTier1Renderer extends EntityRenderer<RocketTier1Entity> {
    public RocketTier1Renderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        new RocketTier1Model<>(p_174008_.bakeLayer(RocketTier1Model.LAYER_LOCATION));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull RocketTier1Entity pEntity) {
        return new ResourceLocation(CGalaxy.MODID, "textures/entity/rocket_tier_1.png");
    }
}
