package net.congueror.cgalaxy.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.congueror.cgalaxy.client.models.RocketTier1Model;
import net.congueror.cgalaxy.item.RocketTier1Item;
import net.minecraft.client.Minecraft;
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

public class RocketItemRenderer extends BlockEntityWithoutLevelRenderer implements IItemRenderProperties {

    RocketTier1Model<?> model;
    private final EntityModelSet entityModelSet;

    public RocketItemRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet entityModelSet) {
        super(p_172550_, entityModelSet);
        this.entityModelSet = entityModelSet;
    }

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return this;
    }

    @Override
    public void onResourceManagerReload(@Nonnull ResourceManager manager) {
        super.onResourceManagerReload(manager);
        model = new RocketTier1Model<>(this.entityModelSet.bakeLayer(RocketTier1Model.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(@Nonnull ItemStack itemStack, @Nonnull ItemTransforms.TransformType type, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource bufferSource, int p_108834_, int p_108835_) {
        Minecraft mc = Minecraft.getInstance();
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.model.renderType(RocketTier1Renderer.TEXTURE), false, itemStack.hasFoil());
        this.model.renderToBuffer(poseStack, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
