package com.congueror.cgalaxy.client;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.compat.curios.CuriosCompat;
import com.congueror.cgalaxy.init.ItemInit;
import com.congueror.cgalaxy.items.OxygenGearItem;
import com.congueror.cgalaxy.items.OxygenTankItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class SpaceSuitLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
    public static final ResourceLocation OXYGEN_GEAR_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/armor/oxygen_gear.png");
    public static final ResourceLocation OXYGEN_TANK_TEXTURE = new ResourceLocation(CGalaxy.MODID, "textures/models/armor/oxygen_tanks.png");


    public SpaceSuitLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ArrayList<Item> items = CuriosCompat.getAllCuriosItems(entitylivingbaseIn);

        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) instanceof OxygenGearItem) {
                    matrixStackIn.push();
                    renderCutoutModel(new OxygenGearModel.BodyOxygenGearModel<>(), OXYGEN_GEAR_TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
                    matrixStackIn.pop();

                    matrixStackIn.push();
                    this.getEntityModel().getModelHead().translateRotate(matrixStackIn);
                    renderCutoutModel(new OxygenGearModel.HeadOxygenGearModel<>(), OXYGEN_GEAR_TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
                    matrixStackIn.pop();
                }
                if (items.get(i) instanceof OxygenTankItem) {
                    if (Collections.frequency(items, items.get(i)) == 1) {
                        if (items.get(i).equals(ItemInit.LIGHT_OXYGEN_TANK.get())) {
                            matrixStackIn.push();
                            renderCutoutModel(new OxygenTankModels.OxygenLightTankModel<>(false), OXYGEN_TANK_TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
                            matrixStackIn.pop();
                        }
                    } else {
                        if (items.get(i).equals(ItemInit.LIGHT_OXYGEN_TANK.get())) {
                            matrixStackIn.push();
                            renderCutoutModel(new OxygenTankModels.OxygenLightTankModel<>(true), OXYGEN_TANK_TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0f, 1.0f, 1.0f);
                            matrixStackIn.pop();
                        }
                    }
                }
            }
        }
    }
}
