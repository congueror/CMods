package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.LunarVillagerModel;
import net.congueror.cgalaxy.client.renderers.layers.LunarVillagerProfessionLayer;
import net.congueror.cgalaxy.entity.villagers.LunarVillager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class LunarVillagerRenderer extends MobRenderer<LunarVillager, LunarVillagerModel<LunarVillager>> {

    public LunarVillagerRenderer(EntityRendererProvider.Context root) {
        super(root, new LunarVillagerModel<>(root.bakeLayer(LunarVillagerModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new LunarVillagerProfessionLayer(this));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull LunarVillager pEntity) {
        return new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_villager.png");
    }
}
