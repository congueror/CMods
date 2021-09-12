package net.congueror.cgalaxy.entity.rockets;

import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.init.CGItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RocketTier1Entity extends RocketEntity {
    public RocketTier1Entity(EntityType<? extends Entity> entity, Level level) {
        super(entity, level);
        capacity = 1000;
    }

    @Override
    public Item getItem() {
        return CGItemInit.ROCKET_TIER_1.get();
    }
}
