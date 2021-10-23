package net.congueror.cgalaxy.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.client.models.LunarVillagerModel;
import net.congueror.cgalaxy.entity.villagers.LunarVillager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import javax.annotation.Nonnull;

public class LunarVillagerProfessionLayer extends RenderLayer<LunarVillager, LunarVillagerModel<LunarVillager>> {
    public LunarVillagerProfessionLayer(RenderLayerParent<LunarVillager, LunarVillagerModel<LunarVillager>> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, @Nonnull LunarVillager pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.isInvisible()) {
            if (pLivingEntity.getProfession().clothesTexture != null) {
                renderColoredCutoutModel(this.getParentModel(), pLivingEntity.getProfession().clothesTexture, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
            }
            if (pLivingEntity.getProfession().professionClothesTexture != null) {
                renderColoredCutoutModel(this.getParentModel(), pLivingEntity.getProfession().professionClothesTexture, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}
