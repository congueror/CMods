package com.congueror.cgalaxy.client.entity_renderers.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class OxygenGearModel {
    public static class BodyOxygenGearModel<T extends LivingEntity> extends EntityModel<T> {

        protected final ModelRenderer BodyOxygenGear;

        public BodyOxygenGearModel() {
            textureWidth = 64;
            textureHeight = 64;

            BodyOxygenGear = new ModelRenderer(this);
            BodyOxygenGear.setRotationPoint(0.0F, 24.0F, 0.0F);
            BodyOxygenGear.setTextureOffset(0, 0).addBox(-3.0F, -23.0F, 3.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
            BodyOxygenGear.setTextureOffset(38, 0).addBox(2.0F, -22.0F, 3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
            BodyOxygenGear.setTextureOffset(38, 0).addBox(-3.0F, -22.0F, 3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
            BodyOxygenGear.setTextureOffset(12, 3).addBox(-1.0F, -24.0F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        }

        @Override
        public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            BodyOxygenGear.render(matrixStack, buffer, packedLight, packedOverlay);
        }
    }

    public static class HeadOxygenGearModel<T extends LivingEntity> extends EntityModel<T> {
        protected final ModelRenderer HeadOxygenGear;

        public HeadOxygenGearModel() {
            textureWidth = 64;
            textureHeight = 64;

            HeadOxygenGear = new ModelRenderer(this);
            HeadOxygenGear.setRotationPoint(0.0F, 24.0F, 0.0F);
            HeadOxygenGear.setTextureOffset(0, 8).addBox(-3.0F, -28.0F, 1.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
        }

        @Override
        public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            HeadOxygenGear.render(matrixStack, buffer, packedLight, packedOverlay);
        }
    }
}
