package com.congueror.clib.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DamageableContainerItem extends Item {
    public DamageableContainerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack1 = new ItemStack(this.getItem());
        stack1.setDamage(itemStack.getDamage() + 1);
        if (stack1.getDamage() > stack1.getMaxDamage()) {
            return new ItemStack(Items.AIR);
        } else {
            return stack1;
        }
    }
}
