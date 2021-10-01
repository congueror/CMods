package net.congueror.cgalaxy.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.OxygenGearModel;
import net.congueror.cgalaxy.client.models.OxygenMaskModel;
import net.congueror.cgalaxy.client.models.OxygenTankModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class SpaceSuitLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public static final ResourceLocation OXYGEN_GEAR_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/oxygen_gear.png");
    public static final ResourceLocation OXYGEN_TANK_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/oxygen_tanks.png");
    public static final ResourceLocation OXYGEN_MASK_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/space_suit.png");

    private final EntityModelSet entityModelSet;

    public SpaceSuitLayer(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        this.entityModelSet = Minecraft.getInstance().getEntityModels();
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, @Nonnull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!(pLivingEntity instanceof Player)) {
            renderOxygenGear(pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
            renderOxygenTank(true, pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
            renderOxygenMask(pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
        }
    }

    protected void renderOxygenGear(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn) {
        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);
        renderColoredCutoutModel(new OxygenGearModel.Body<>(entityModelSet.bakeLayer(OxygenGearModel.Body.LAYER_LOCATION)), OXYGEN_GEAR_TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();

        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        renderColoredCutoutModel(new OxygenGearModel.Head<>(entityModelSet.bakeLayer(OxygenGearModel.Head.LAYER_LOCATION)), OXYGEN_GEAR_TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    protected void renderOxygenTank(boolean two, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn) {
        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);
        renderColoredCutoutModel(new OxygenTankModel<>(entityModelSet.bakeLayer(OxygenTankModel.LAYER_LOCATION), two), OXYGEN_TANK_TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    protected void renderOxygenMask(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn) {
        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        renderColoredCutoutModel(new OxygenMaskModel<>(entityModelSet.bakeLayer(OxygenMaskModel.LAYER_LOCATION)), OXYGEN_MASK_TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }
}
