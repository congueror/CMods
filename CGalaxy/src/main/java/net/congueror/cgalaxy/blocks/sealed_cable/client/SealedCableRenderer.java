package net.congueror.cgalaxy.blocks.sealed_cable.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SealedCableRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private final BlockEntityRendererProvider.Context ctx;
    private final ResourceLocation texture;
    private final ResourceLocation texture1;

    public SealedCableRenderer(BlockEntityRendererProvider.Context ctx, ResourceLocation texture, ResourceLocation texture1) {
        this.ctx = ctx;
        this.texture = texture;
        this.texture1 = texture1;
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer consumer = pBufferSource.getBuffer(RenderType.cutout());

        Direction facing = pBlockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        Quaternion quaternion = null;
        Quaternion quaternion1 = null;
        switch (facing) {
            case UP -> {
                quaternion = new Quaternion(90, 0, 0, true);
                quaternion1 = new Quaternion(-90, 0, 0, true);
            }
            case DOWN -> {
                quaternion = new Quaternion(-90, 0, 0, true);
                quaternion1 = new Quaternion(90, 0, 0, true);
            }
            case EAST -> {
                quaternion = new Quaternion(0, -90, 0, true);
                quaternion1 = new Quaternion(0, 90, 0, true);
            }
            case WEST -> {
                quaternion = new Quaternion(0, 90, 0, true);
                quaternion1 = new Quaternion(0, -90, 0, true);
            }
            case NORTH -> {
                quaternion = new Quaternion(0, 0, 0, true);
                quaternion1 = new Quaternion(0, 180, 0, true);
            }
            case SOUTH -> {
                quaternion = new Quaternion(0, 180, 0, true);
                quaternion1 = new Quaternion(0, 0, 0, true);
            }
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture);
        TextureAtlasSprite sprite1 = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture1);

        pPackedLight = 0xF00FF;

        pPoseStack.pushPose();

        pPoseStack.translate(0.5, 0.5, 0.5);
        pPoseStack.mulPose(quaternion);
        pPoseStack.translate(-0.5, -0.5, -0.51);

        Matrix4f matrix4f = pPoseStack.last().pose();
        Matrix3f matrix3f = pPoseStack.last().normal();

        consumer.vertex(matrix4f, 0, 1, 0).color(255, 255, 255, 255).uv(sprite.getU1(), sprite.getV0()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();
        consumer.vertex(matrix4f, 1, 1, 0).color(255, 255, 255, 255).uv(sprite.getU0(), sprite.getV0()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();
        consumer.vertex(matrix4f, 1, 0, 0).color(255, 255, 255, 255).uv(sprite.getU0(), sprite.getV1()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();
        consumer.vertex(matrix4f, 0, 0, 0).color(255, 255, 255, 255).uv(sprite.getU1(), sprite.getV1()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();

        pPoseStack.popPose();

        pPoseStack.pushPose();

        pPoseStack.translate(0.5, 0.5, 0.5);
        pPoseStack.mulPose(quaternion1);
        pPoseStack.translate(-0.5, -0.5, -0.51);

        matrix4f = pPoseStack.last().pose();
        matrix3f = pPoseStack.last().normal();

        consumer.vertex(matrix4f, 0, 1, 0).color(255, 255, 255, 255).uv(sprite1.getU1(), sprite1.getV0()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();
        consumer.vertex(matrix4f, 1, 1, 0).color(255, 255, 255, 255).uv(sprite1.getU0(), sprite1.getV0()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();
        consumer.vertex(matrix4f, 1, 0, 0).color(255, 255, 255, 255).uv(sprite1.getU0(), sprite1.getV1()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();
        consumer.vertex(matrix4f, 0, 0, 0).color(255, 255, 255, 255).uv(sprite1.getU1(), sprite1.getV1()).uv2(pPackedLight).normal(matrix3f, facing.getStepX(), facing.getStepY(), facing.getStepZ()).overlayCoords(pPackedOverlay).endVertex();

        pPoseStack.popPose();
    }
}
