package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.LunarZombieVillagerModel;
import net.congueror.cgalaxy.client.renderers.layers.LunarZombieVillagerLayer;
import net.congueror.cgalaxy.entity.villagers.LunarZombieVillager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class LunarZombieVillagerRenderer extends HumanoidMobRenderer<LunarZombieVillager, LunarZombieVillagerModel<LunarZombieVillager>> {

    public LunarZombieVillagerRenderer(EntityRendererProvider.Context root) {
        super(root, new LunarZombieVillagerModel<>(root.bakeLayer(LunarZombieVillagerModel.LAYER_LOCATION)), 0.4F);
        this.addLayer(new LunarZombieVillagerLayer(this));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull LunarZombieVillager pEntity) {
        return new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_zombie_villager.png");
    }
}
