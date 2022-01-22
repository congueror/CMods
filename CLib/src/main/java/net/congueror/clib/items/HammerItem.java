package net.congueror.clib.items;

import net.congueror.clib.items.generic.CLItem;
import net.congueror.clib.init.CLItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Random;

public class HammerItem extends CLItem {
    public HammerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level worldIn, BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
        Random rand = new Random();
        if (state.getBlock().equals(Blocks.SAND)) {
            stack.setDamageValue(stack.getDamageValue() + 2);
            if (rand.nextDouble() >= 0.5) {
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(CLItemInit.SILICA.get())));
            }
            if (stack.getDamageValue() == stack.getMaxDamage()) {
                stack.shrink(1);
                worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1, 0, false);
            }
            return true;
        }
        return false;
    }
}
