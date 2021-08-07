package com.congueror.cgalaxy.client;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.ItemInit;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

public class SpaceSuitLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/armor/oxygen_gear.png");
    public final OxygenGearModel.BodyOxygenGearModel<T> bodyModel = new OxygenGearModel.BodyOxygenGearModel<>();
    public final OxygenGearModel.HeadOxygenGearModel<T> headModel = new OxygenGearModel.HeadOxygenGearModel<>();

    public SpaceSuitLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.OXYGEN_GEAR.get(), entitylivingbaseIn).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            matrixStackIn.push();
            renderCutoutModel(bodyModel, TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
            matrixStackIn.pop();

            matrixStackIn.push();
            this.getEntityModel().getModelHead().translateRotate(matrixStackIn);
            renderCutoutModel(headModel, TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
            matrixStackIn.pop();
        }
    }
}
