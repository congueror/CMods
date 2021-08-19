package com.congueror.cgalaxy.client.entity_renderers.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class OxygenTankModels {
    public static class OxygenLightTankModel<T extends LivingEntity> extends EntityModel<T> {
        private final ModelRenderer Tank1;
        private final ModelRenderer Tank2;

        public OxygenLightTankModel(boolean twoTanks) {
            textureWidth = 64;
            textureHeight = 64;

            Tank1 = new ModelRenderer(this);
            Tank1.setRotationPoint(0.0F, 24.0F, 0.0F);
            Tank1.setTextureOffset(0, 0).addBox(-4.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);
            Tank1.setTextureOffset(12, 5).addBox(-4.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);

            Tank2 = new ModelRenderer(this);
            if (twoTanks) {
                Tank2.setRotationPoint(0.0F, 24.0F, 0.0F);
                Tank2.setTextureOffset(0, 0).addBox(1.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);
                Tank2.setTextureOffset(12, 5).addBox(1.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
            }
        }

        @Override
        public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Tank1.render(matrixStack, buffer, packedLight, packedOverlay);
            Tank2.render(matrixStack, buffer, packedLight, packedOverlay);
        }
    }
}
