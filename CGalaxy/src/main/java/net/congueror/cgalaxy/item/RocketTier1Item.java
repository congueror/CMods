package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.client.renderers.RocketItemRenderer;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.entity.rockets.RocketTier1;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class RocketTier1Item extends AbstractRocketItem {

    public RocketTier1Item(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public AbstractRocket newRocketEntity(Level level, int fuel) {
        RocketTier1 rocket = new RocketTier1(CGEntityTypeInit.ROCKET_TIER_1.get(), level);
        rocket.fill(fuel);
        return rocket;
    }

    @Override
    public BlockEntityWithoutLevelRenderer newBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        return new RocketItemRenderer(dispatcher, modelSet);
    }
}
