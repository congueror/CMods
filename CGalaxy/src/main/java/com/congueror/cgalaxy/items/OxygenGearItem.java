package com.congueror.cgalaxy.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class OxygenGearItem extends Item implements ICurio {
    public OxygenGearItem(Properties properties) {
        super(properties.maxStackSize(1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void curioAnimate(String identifier, int index, LivingEntity livingEntity) {

    }
}
