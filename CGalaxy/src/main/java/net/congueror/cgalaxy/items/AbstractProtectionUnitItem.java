package net.congueror.cgalaxy.items;

import net.congueror.clib.items.AbstractEnergyItem;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractProtectionUnitItem extends AbstractEnergyItem {
    int capacity;
    public AbstractProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties);
        this.capacity = capacity;
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return capacity;
    }
}
