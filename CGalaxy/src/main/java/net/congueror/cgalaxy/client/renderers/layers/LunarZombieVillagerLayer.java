package net.congueror.cgalaxy.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.LunarZombieVillagerModel;
import net.congueror.cgalaxy.entity.villagers.LunarZombieVillager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class LunarZombieVillagerLayer extends RenderLayer<LunarZombieVillager, LunarZombieVillagerModel<LunarZombieVillager>> {
    public LunarZombieVillagerLayer(RenderLayerParent<LunarZombieVillager, LunarZombieVillagerModel<LunarZombieVillager>> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, @Nonnull LunarZombieVillager pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        renderColoredCutoutModel(this.getParentModel(), new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_villager_clothes.png"), pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
    }
}
