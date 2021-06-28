package com.congueror.clib.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModFuelItem extends Item {
    int burnTime;

    public ModFuelItem(Properties properties, int burnTime) {
        super(properties);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return burnTime;
    }
}
