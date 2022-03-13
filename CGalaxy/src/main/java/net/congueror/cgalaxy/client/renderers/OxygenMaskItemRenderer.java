package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.OxygenMaskModel;
import net.congueror.cgalaxy.items.OxygenMaskItem;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class OxygenMaskItemRenderer extends BlockEntityWithoutLevelRenderer {
    OxygenMaskModel<Entity> model;

    public OxygenMaskItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet set) {
        super(dispatcher, set);
        this.model = new OxygenMaskModel<>(set.bakeLayer(OxygenMaskModel.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(ItemStack itemStack, @Nonnull ItemTransforms.TransformType type, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource source, int p_108834_, int p_108835_) {
        if (itemStack.getItem() instanceof OxygenMaskItem) {
            poseStack.pushPose();
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(source, this.model.renderType(CGalaxy.location("textures/models/space_suit.png")), false, itemStack.hasFoil());
            this.model.renderItemToBuffer(poseStack, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F, type);
            poseStack.popPose();
        }
        super.renderByItem(itemStack, type, poseStack, source, p_108834_, p_108835_);
    }
}
