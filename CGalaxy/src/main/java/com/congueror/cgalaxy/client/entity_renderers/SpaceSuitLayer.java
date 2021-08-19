package com.congueror.cgalaxy.client.entity_renderers;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.client.entity_renderers.models.OxygenGearModel;
import com.congueror.cgalaxy.client.entity_renderers.models.OxygenMaskModel;
import com.congueror.cgalaxy.client.entity_renderers.models.OxygenTankModels;
import com.congueror.cgalaxy.compat.curios.CuriosCompat;
import com.congueror.cgalaxy.entities.AstroEndermanEntity;
import com.congueror.cgalaxy.init.ItemInit;
import com.congueror.cgalaxy.items.OxygenGearItem;
import com.congueror.cgalaxy.items.OxygenTankItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;

public class SpaceSuitLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    public static final ResourceLocation OXYGEN_GEAR_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/armor/oxygen_gear.png");
    public static final ResourceLocation OXYGEN_TANK_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/armor/oxygen_tanks.png");
    public static final ResourceLocation OXYGEN_MASK_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/armor/space_suit.png");


    public SpaceSuitLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ArrayList<Item> items = CuriosCompat.getAllCuriosItems(entitylivingbaseIn);

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) instanceof OxygenGearItem) {
                    renderOxygenGear(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
                }
                if (items.get(i) instanceof OxygenTankItem) {
                    if (Collections.frequency(items, items.get(i)) == 1) {
                        if (items.get(i).equals(ItemInit.LIGHT_OXYGEN_TANK.get())) {
                            renderOxygenTank(false, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
                        }
                    } else {
                        if (items.get(i).equals(ItemInit.LIGHT_OXYGEN_TANK.get())) {
                            renderOxygenTank(true, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
                        }
                    }
                }
            }
        }

        if (!(entitylivingbaseIn instanceof PlayerEntity)) {
            renderOxygenGear(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
            renderOxygenTank(true, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
            renderOxygenMask(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn);
        }
    }

    protected void renderOxygenGear(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn) {
        matrixStack.push();
        this.getEntityModel().bipedBody.translateRotate(matrixStack);
        renderCutoutModel(new OxygenGearModel.BodyOxygenGearModel<>(), OXYGEN_GEAR_TEXTURE, matrixStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();

        matrixStack.push();
        this.getEntityModel().getModelHead().translateRotate(matrixStack);
        renderCutoutModel(new OxygenGearModel.HeadOxygenGearModel<>(), OXYGEN_GEAR_TEXTURE, matrixStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

    protected void renderOxygenTank(boolean two, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn) {
        matrixStack.push();
        this.getEntityModel().bipedBody.translateRotate(matrixStack);
        renderCutoutModel(new OxygenTankModels.OxygenLightTankModel<>(two), OXYGEN_TANK_TEXTURE, matrixStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

    protected void renderOxygenMask(MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn) {
        matrixStack.push();
        this.getEntityModel().getModelHead().translateRotate(matrixStack);
        renderCutoutModel(new OxygenMaskModel<>(), OXYGEN_MASK_TEXTURE, matrixStack, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }
}
