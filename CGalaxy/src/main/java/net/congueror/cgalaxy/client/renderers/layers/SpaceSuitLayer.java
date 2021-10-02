package net.congueror.cgalaxy.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.OxygenGearModel;
import net.congueror.cgalaxy.client.models.OxygenMaskModel;
import net.congueror.cgalaxy.client.models.OxygenTankModels;
import net.congueror.cgalaxy.item.OxygenGearItem;
import net.congueror.cgalaxy.item.OxygenTankItem;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

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
        if (pLivingEntity instanceof Player player) {
            AtomicBoolean two = new AtomicBoolean(false);
            SpaceSuitUtils.deserializeContents(player).stream().map(ItemStack::getItem).forEach(item -> {
                if (item instanceof OxygenGearItem) renderOxygenGear(pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
                if (item instanceof OxygenTankItem tank) {
                    //noinspection unchecked
                    renderOxygenTank((EntityModel<T>) tank.getModel(two.getAndSet(true)), pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
                }
            });
        }
        if (!(pLivingEntity instanceof Player)) {
            renderOxygenGear(pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
            renderOxygenTank(new OxygenTankModels.Heavy<>(entityModelSet.bakeLayer(OxygenTankModels.Heavy.LAYER_LOCATION), true), pMatrixStack, pBuffer, pPackedLight, pLivingEntity);
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

    protected void renderOxygenTank(EntityModel<T> tankModel, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn) {
        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);
        renderColoredCutoutModel(tankModel, OXYGEN_TANK_TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    protected void renderOxygenMask(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn) {
        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        renderColoredCutoutModel(new OxygenMaskModel<>(entityModelSet.bakeLayer(OxygenMaskModel.LAYER_LOCATION)), OXYGEN_MASK_TEXTURE, poseStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }
}
