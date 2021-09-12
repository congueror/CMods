package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.minecraft.world.level.Level;

public class RocketTier1Item extends RocketItem {
    public RocketTier1Item(Properties properties) {
        super(properties);
    }

    @Override
    public RocketEntity rocketEntity(Level level) {
        return new RocketTier1Entity(CGEntityTypeInit.ROCKET_TIER_1.get(), level);
    }
}
