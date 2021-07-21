package com.congueror.clib.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
