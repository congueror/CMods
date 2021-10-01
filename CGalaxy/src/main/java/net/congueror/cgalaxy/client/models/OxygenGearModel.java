package net.congueror.cgalaxy.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class OxygenGearModel {
    public static class Head<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "oxygen_gear_head"), "main");
        private final ModelPart HeadOxygenGear;

        public Head(ModelPart root) {
            this.HeadOxygenGear = root.getChild("HeadOxygenGear");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            partdefinition.addOrReplaceChild("HeadOxygenGear", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -28.0F, 1.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(@Nonnull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        }

        @Override
        public void renderToBuffer(@Nonnull PoseStack pPoseStack, @Nonnull VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            HeadOxygenGear.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }

    public static class Body<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "oxygen_gear_body"), "main");
        private final ModelPart BodyOxygenGear;

        public Body(ModelPart root) {
            this.BodyOxygenGear = root.getChild("BodyOxygenGear");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();
            partdefinition.addOrReplaceChild("BodyOxygenGear", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -23.0F, 3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(38, 0).addBox(2.0F, -22.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(38, 0).addBox(-3.0F, -22.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 3).addBox(-1.0F, -24.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(@Nonnull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        }

        @Override
        public void renderToBuffer(@Nonnull PoseStack pPoseStack, @Nonnull VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            BodyOxygenGear.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }
}
