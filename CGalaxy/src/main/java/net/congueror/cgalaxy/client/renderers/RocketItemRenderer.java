package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.client.models.RocketTier1Model;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.congueror.cgalaxy.item.RocketTier1Item;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;

public class RocketItemRenderer extends BlockEntityWithoutLevelRenderer {

    RocketTier1Model model;

    public RocketItemRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet entityModelSet) {
        super(p_172550_, entityModelSet);
        this.model = new RocketTier1Model(entityModelSet.bakeLayer(RocketTier1Model.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(@Nonnull ItemStack itemStack, @Nonnull ItemTransforms.TransformType type, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource bufferSource, int p_108834_, int p_108835_) {
        if (itemStack.getItem() instanceof RocketTier1Item) {
            poseStack.pushPose();//TODO: Player hand rotation
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.model.renderType(RocketTier1Renderer.TEXTURE), false, itemStack.hasFoil());
            this.model.renderItemToBuffer(poseStack, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F, type);
            poseStack.popPose();
        }
        super.renderByItem(itemStack, type, poseStack, bufferSource, p_108834_, p_108835_);
    }
}
