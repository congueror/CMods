package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class MartianSkeletonRenderer extends SkeletonRenderer {
    public MartianSkeletonRenderer(EntityRendererProvider.Context p_174380_) {
        super(p_174380_);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
        return CGalaxy.location("textures/entity/martian_skeleton.png");
    }
}
