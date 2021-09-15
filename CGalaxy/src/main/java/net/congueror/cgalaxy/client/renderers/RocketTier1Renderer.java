package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class RocketTier1Renderer extends EntityRenderer<RocketTier1Entity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/entity/rocket_tier_1.png");
    protected final EntityModel<RocketTier1Entity> model;

    public RocketTier1Renderer(EntityRendererProvider.Context p_174008_, EntityModel<RocketTier1Entity> model) {
        super(p_174008_);
        this.model = model;
    }

    @Override
    public void render(@Nonnull RocketTier1Entity pEntity, float pEntityYaw, float pPartialTicks, @Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull RocketTier1Entity pEntity) {
        return TEXTURE;
    }
}
