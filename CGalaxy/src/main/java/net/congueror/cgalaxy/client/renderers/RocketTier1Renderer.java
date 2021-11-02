package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.rockets.RocketTier1;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class RocketTier1Renderer extends EntityRenderer<RocketTier1> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/entity/rocket_tier_1.png");
    protected final EntityModel<RocketTier1> model;

    public RocketTier1Renderer(EntityRendererProvider.Context p_174008_, EntityModel<RocketTier1> model) {
        super(p_174008_);
        this.model = model;
    }

    @Override
    public void render(@Nonnull RocketTier1 pEntity, float pEntityYaw, float pPartialTicks, @Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight) {
        VertexConsumer consumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        model.setupAnim(pEntity, pPartialTicks, 0, 0, 0, 0);
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0f, 1.5f, 0.0f);
        pMatrixStack.mulPose(Vector3f.XN.rotationDegrees(180));
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(pEntityYaw));
        model.renderToBuffer(pMatrixStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull RocketTier1 pEntity) {
        return TEXTURE;
    }
}
