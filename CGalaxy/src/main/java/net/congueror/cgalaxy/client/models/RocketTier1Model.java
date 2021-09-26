package net.congueror.cgalaxy.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class RocketTier1Model<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "rocket_tier_1"), "main");
    private final ModelPart Rocket;

    private static int rotation, rotation1;

    public RocketTier1Model(ModelPart root) {
        this.Rocket = root.getChild("Rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("Rocket", CubeListBuilder.create().texOffs(0, 0).addBox(11.0F, -8.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -8.0F, -12.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-12.0F, -8.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -8.0F, 11.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 10).addBox(4.0F, -31.0F, -8.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(37, 10).addBox(-7.0F, -31.0F, -8.0F, 3.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, -31.0F, -8.1F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -31.0F, -8.1F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -31.0F, -8.1F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -25.0F, -8.1F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 0).addBox(-7.0F, -39.0F, -8.0F, 14.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 19).addBox(-7.0F, -24.0F, -8.0F, 14.0F, 19.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 1).addBox(-7.0F, -39.0F, 7.0F, 14.0F, 34.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 0).addBox(-8.0F, -39.0F, -7.0F, 1.0F, 34.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -40.0F, -8.0F, 1.0F, 35.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -40.0F, 7.0F, 1.0F, 35.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -40.0F, 7.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -40.0F, -8.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -40.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(7.0F, -40.0F, -7.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(7.0F, -40.0F, 7.0F, 1.0F, 35.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(7.0F, -40.0F, -8.0F, 1.0F, 35.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(10.0F, -10.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -10.0F, -11.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-11.0F, -10.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -10.0F, 10.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(9.0F, -11.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -11.0F, -10.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-10.0F, -11.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -11.0F, 9.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(12.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -6.0F, -13.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-13.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -6.0F, 12.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(13.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -4.0F, -14.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -4.0F, 13.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(8.0F, -12.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -12.0F, -9.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -12.0F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -12.0F, 8.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 1).addBox(7.0F, -39.0F, -7.0F, 1.0F, 34.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-8.0F, -5.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(89, 20).addBox(-1.0F, -68.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 9).addBox(-1.0F, -66.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-7.0F, -41.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 53).addBox(-6.0F, -43.0F, -6.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(3, 53).addBox(-5.0F, -45.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(3, 53).addBox(-4.0F, -47.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(3, 53).addBox(-3.0F, -49.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(3, 53).addBox(-2.0F, -51.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(444, 32).addBox(-7.0F, -5.0F, -6.0F, 11.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(32, 17).addBox(-7.0F, -40.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(104, 0).addBox(-8.0F, -5.0F, -8.0F, 1.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(14, 2).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -2.0F, 3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-2.0F, -3.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -1.0F, 4.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(4.0F, -1.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -1.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 256);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    /**
     * Sets and increments the rotation of the rocket item. Called from a {@link net.minecraftforge.event.TickEvent.ClientTickEvent} event
     */
    public static void setupItemRotation() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (rotation1 > -1) {
                rotation++;
                rotation1 = 0;
            } else {
                rotation1++;
            }
            if (rotation > 360) {
                rotation = 0;
            }
        }
    }

    public void renderItemToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, @Nonnull ItemTransforms.TransformType type) {
        poseStack.pushPose();
        switch (type) {
            case THIRD_PERSON_RIGHT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.2D, 0.4D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case THIRD_PERSON_LEFT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.7D, 0.4D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case FIRST_PERSON_RIGHT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.2D, 2D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case FIRST_PERSON_LEFT_HAND -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.7D, 2D, 0.0D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case HEAD -> {
                poseStack.scale(1.0F, 1.0F, 1.0F);
                poseStack.translate(0.5D, 2D, 0.5D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(0.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
            case GUI -> {
                poseStack.scale(0.45F, 0.45F, 0.45F);
                poseStack.translate(1.1D, 1.5D, 0.9D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F + (float) rotation));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
            case GROUND -> {
                poseStack.scale(0.45F, 0.45F, 0.45F);
                poseStack.translate(1.1D, 1.5D, 1D);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(0.0F));
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F + (float) rotation));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
        }
        renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Rocket.render(poseStack, buffer, packedLight, packedOverlay);
    }
}