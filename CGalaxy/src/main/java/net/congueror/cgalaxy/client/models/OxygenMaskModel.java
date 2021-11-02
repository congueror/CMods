package net.congueror.cgalaxy.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class OxygenMaskModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "oxygen_mask"), "main");
    private final ModelPart Mask;

    public OxygenMaskModel(ModelPart root) {
        Mask = root.getChild("Mask");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("Mask", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@Nonnull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack pPoseStack, @Nonnull VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        Mask.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }

    public void renderItemToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, @Nonnull ItemTransforms.TransformType type) {
        poseStack.pushPose();
        switch (type) {
            case THIRD_PERSON_RIGHT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.75D, 0.4D, 0.22D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case THIRD_PERSON_LEFT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(1.25D, 0.4D, 0.22D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.75D, 0.4D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case FIRST_PERSON_LEFT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(1.25D, 0.4D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case HEAD -> {
            }
            case GUI -> {
                poseStack.scale(1.1F, 1.1F, 1.1F);
                poseStack.translate(0.455D, 0.25D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(30.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(225.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
            case GROUND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(1D, 1D, 1D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
        }
        renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
