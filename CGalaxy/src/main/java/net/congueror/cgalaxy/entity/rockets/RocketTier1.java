package net.congueror.cgalaxy.entity.rockets;

import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RocketTier1 extends AbstractRocket {
    public RocketTier1(EntityType<? extends Entity> entity, Level level) {
        super(entity, level, 1);
        capacity = 1000;
    }

    @Override
    public Item getItem() {
        return CGItemInit.ROCKET_TIER_1.get();
    }
}
