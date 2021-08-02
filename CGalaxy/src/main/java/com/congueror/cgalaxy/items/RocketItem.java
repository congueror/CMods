package com.congueror.cgalaxy.items;

import com.congueror.cgalaxy.block.launch_pad.LaunchPadBlock;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.entities.rockets.RocketTier1Entity;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.EntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RocketItem extends Item {

    public RocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        BlockPos pos = context.getPos();
        BlockState blockState = world.getBlockState(pos);
        if (blockState.matchesBlock(BlockInit.LAUNCH_PAD.get())) {
            LaunchPadBlock launchPad = (LaunchPadBlock) (blockState.getBlock());
            ItemStack stack = context.getItem();
            RocketEntity entity = rocketEntity(world);
            if (launchPad.spawnRocket(world, pos, entity)) {
                stack.shrink(1);
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.PASS;
    }

    public RocketEntity rocketEntity(World world) {
        return new RocketTier1Entity(EntityTypeInit.ROCKET_TIER_1.get(), world);
    }
}
