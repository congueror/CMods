package net.congueror.cgalaxy.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public abstract class AbstractRocketModel<T extends Entity> extends EntityModel<T> {

    private static int rotation;
    private static int rotation1;

    /**
     * Sets and increments the rotation of the rocket item. Called from a {@link net.minecraftforge.event.TickEvent.ClientTickEvent} event
     * TODO: Come up with better solution?
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
}
