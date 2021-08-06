package com.congueror.clib.items;

import com.congueror.clib.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class HammerItem extends DamageableContainerItem {
    public HammerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        Random rand = new Random();
        if (state.getBlock().matchesBlock(Blocks.SAND)) {
            stack.setDamage(stack.getDamage() + 2);
            if (rand.nextDouble() >= 0.5) {
                worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.SILICA.get())));
            }
            if (stack.getDamage() == stack.getMaxDamage()) {
                stack.shrink(1);
                worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 0, false);
            }
            return true;
        }
        return false;
    }
}
