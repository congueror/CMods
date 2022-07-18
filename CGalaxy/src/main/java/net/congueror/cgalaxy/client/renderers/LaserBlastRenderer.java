package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.entity.LaserBlast;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class LaserBlastRenderer extends EntityRenderer<LaserBlast> {
    public LaserBlastRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(LaserBlast pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        Entity owner = pEntity.getOwner();
        if (owner != null) {
            float f = 1.0f;
            float f1 = (float)pEntity.level.getGameTime() + pPartialTicks;
            float f2 = f1 * 0.5F % 1.0F;
            float f3 = pEntity.getEyeHeight();
            pMatrixStack.pushPose();
            pMatrixStack.translate(0.0D, f3, 0.0D);
            Vec3 vec3 = this.getPosition(owner, (double)owner.getBbHeight() * 0.6D, pPartialTicks);
            Vec3 vec31 = this.getPosition(pEntity, f3, pPartialTicks);
            Vec3 vec32 = vec3.subtract(vec31);
            float f4 = (float)(vec32.length() + 0.0D);
            vec32 = vec32.normalize();
            float f5 = (float)Math.acos(vec32.y);
            float f6 = (float)Math.atan2(vec32.z, vec32.x);
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(f5 * (180F / (float)Math.PI)));
            int i = 1;
            float f7 = f1 * 0.05F * -1.5F;
            float f8 = f * f;
            int j = 255;
            int k = 0;
            int l = 0;
            float f9 = 0.2F;
            float f10 = 0.282F;
            float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
            float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
            float f13 = Mth.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
            float f14 = Mth.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
            float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
            float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
            float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
            float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
            float f19 = Mth.cos(f7 + (float)Math.PI) * 0.2F;
            float f20 = Mth.sin(f7 + (float)Math.PI) * 0.2F;
            float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
            float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
            float f23 = Mth.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
            float f24 = Mth.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
            float f25 = Mth.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
            float f26 = Mth.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
            float f27 = 0.0F;
            float f28 = 0.4999F;
            float f29 = -1.0F + f2;
            float f30 = f4 * 2.5F + f29;
            VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation("textures/entity/guardian_beam.png")));
            PoseStack.Pose posestack$pose = pMatrixStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();
            vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
            vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
            vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
            vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
            float f31 = 0.0F;
            if (pEntity.tickCount % 2 == 0) {
                f31 = 0.5F;
            }

            vertex(vertexconsumer, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
            vertex(vertexconsumer, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
            vertex(vertexconsumer, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
            vertex(vertexconsumer, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
            pMatrixStack.popPose();
        }
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float z, int red, int green, int blue, float p_114851_, float p_114852_) {
        consumer.vertex(matrix4f, x, y, z).color(red, green, blue, 255).uv(p_114851_, p_114852_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private Vec3 getPosition(Entity entity, double endOffset, float delta) {
        double d0 = Mth.lerp(delta, entity.xOld, entity.getX());
        double d1 = Mth.lerp(delta, entity.yOld, entity.getY()) + endOffset;
        double d2 = Mth.lerp(delta, entity.zOld, entity.getZ());
        return new Vec3(d0, d1, d2);
    }

    @Override
    public ResourceLocation getTextureLocation(LaserBlast pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
