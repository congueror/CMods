package com.congueror.cgalaxy.items;

import net.minecraft.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class OxygenGearItem extends Item implements ICurio {
    public OxygenGearItem(Properties properties) {
        super(properties.maxStackSize(1));
    }
}
