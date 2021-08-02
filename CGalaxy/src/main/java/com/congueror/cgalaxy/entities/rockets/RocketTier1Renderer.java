package com.congueror.cgalaxy.entities.rockets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class RocketTier1Renderer extends EntityModel<Entity> {
    private final ModelRenderer Rocket;

    public RocketTier1Renderer() {
        textureWidth = 512;
        textureHeight = 256;
        Rocket = new ModelRenderer(this);
        Rocket.setRotationPoint(0.0F, 23.0F, 0.0F);
        Rocket.setTextureOffset(0, 0).addBox(11.0F, -8.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -8.0F, -12.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-12.0F, -8.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -8.0F, 11.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(35, 10).addBox(4.0F, -31.0F, -8.0F, 3.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(37, 10).addBox(-7.0F, -31.0F, -8.0F, 3.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(3.0F, -31.0F, -8.1F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-4.0F, -31.0F, -8.1F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-3.0F, -31.0F, -8.1F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-3.0F, -25.0F, -8.1F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 0).addBox(-7.0F, -39.0F, -8.0F, 14.0F, 8.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 19).addBox(-7.0F, -24.0F, -8.0F, 14.0F, 19.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(35, 1).addBox(-7.0F, -39.0F, 7.0F, 14.0F, 34.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(35, 0).addBox(-8.0F, -39.0F, -7.0F, 1.0F, 34.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-8.0F, -40.0F, -8.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-8.0F, -40.0F, 7.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-7.0F, -40.0F, 7.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-7.0F, -40.0F, -8.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-8.0F, -40.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(7.0F, -40.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(7.0F, -40.0F, 7.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(7.0F, -40.0F, -8.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(10.0F, -10.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -10.0F, -11.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-11.0F, -10.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -10.0F, 10.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(9.0F, -11.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -11.0F, -10.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-10.0F, -11.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -11.0F, 9.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(12.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, -13.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-13.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -6.0F, 12.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(13.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -4.0F, -14.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-14.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -4.0F, 13.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(8.0F, -12.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -12.0F, -9.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-9.0F, -12.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-1.0F, -12.0F, 8.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(35, 1).addBox(7.0F, -39.0F, -7.0F, 1.0F, 34.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(68, 0).addBox(-8.0F, -5.0F, -8.0F, 16.0F, 1.0F, 16.0F, 0.0F, false);
        Rocket.setTextureOffset(89, 20).addBox(-1.0F, -68.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(6, 9).addBox(-1.0F, -66.0F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 52).addBox(-7.0F, -41.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 53).addBox(-6.0F, -43.0F, -6.0F, 12.0F, 2.0F, 12.0F, 0.0F, false);
        Rocket.setTextureOffset(3, 53).addBox(-5.0F, -45.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(3, 53).addBox(-4.0F, -47.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(3, 53).addBox(-3.0F, -49.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(3, 53).addBox(-2.0F, -51.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
        Rocket.setTextureOffset(444, 32).addBox(-7.0F, -5.0F, -6.0F, 11.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(32, 17).addBox(-7.0F, -40.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(104, 0).addBox(-8.0F, -5.0F, -8.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(14, 2).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 0.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, 3.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(3.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 6).addBox(-2.0F, -3.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, 4.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(4.0F, -1.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Rocket.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}