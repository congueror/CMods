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

public class OxygenTankModels {
    public static class Light<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "light_oxygen_tank"), "main");
        private final ModelPart Tank1;
        private final ModelPart Tank2;
        private final boolean two;

        public Light(ModelPart root, boolean twoTanks) {
            this.Tank1 = root.getChild("Tank1");
            this.Tank2 = root.getChild("Tank2");
            this.two = twoTanks;
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            partdefinition.addOrReplaceChild("Tank1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 5).addBox(-4.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            partdefinition.addOrReplaceChild("Tank2", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 5).addBox(1.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Tank1.render(poseStack, buffer, packedLight, packedOverlay);
            if (two)
                Tank2.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }

    public static class Medium<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "medium_oxygen_tank"), "main");
        private final ModelPart Tank1;
        private final ModelPart Tank2;
        private final boolean two;

        public Medium(ModelPart root, boolean twoTanks) {
            this.Tank1 = root.getChild("Tank1");
            this.Tank2 = root.getChild("Tank2");
            this.two = twoTanks;
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            partdefinition.addOrReplaceChild("Tank1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 10).addBox(-4.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            partdefinition.addOrReplaceChild("Tank2", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 10).addBox(1.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Tank1.render(poseStack, buffer, packedLight, packedOverlay);
            if (two)
                Tank2.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }

    public static class Heavy<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "heavy_oxygen_tank"), "main");
        private final ModelPart Tank1;
        private final ModelPart Tank2;
        private final boolean two;

        public Heavy(ModelPart root, boolean twoTanks) {
            this.Tank1 = root.getChild("Tank1");
            this.Tank2 = root.getChild("Tank2");
            this.two = twoTanks;
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            partdefinition.addOrReplaceChild("Tank1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 0).addBox(-4.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            partdefinition.addOrReplaceChild("Tank2", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(12, 0).addBox(1.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Tank1.render(poseStack, buffer, packedLight, packedOverlay);
            if (two)
                Tank2.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}