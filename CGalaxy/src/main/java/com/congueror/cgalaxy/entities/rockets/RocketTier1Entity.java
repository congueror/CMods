package com.congueror.cgalaxy.entities.rockets;

import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RocketTier1Entity extends RocketEntity {
    public RocketTier1Entity(EntityType<? extends RocketEntity> entity, World world) {
        super(entity, world);
        capacity = 1000;
    }

    @Override
    public Item getItem() {
        return ItemInit.ROCKET_TIER_1.get();
    }
}
