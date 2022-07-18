package net.congueror.clib.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

public class WrenchItem extends Item {
    public WrenchItem(Properties pProperties) {
        super(pProperties);
    }

    public static InteractionResult wrenchUse(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            if (stack.getItem() instanceof WrenchItem) {
                if (pState.hasProperty(BlockStateProperties.FACING)) {
                    BlockState oldState = pLevel.getBlockState(pPos);
                    int dir = pState.getValue(BlockStateProperties.FACING).ordinal();
                    int max = Direction.values().length;

                    dir++;
                    if (dir >= max)
                        dir = 0;
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(BlockStateProperties.FACING, Direction.values()[dir]));
                    pLevel.sendBlockUpdated(pPos, oldState, pState.setValue(BlockStateProperties.FACING, Direction.values()[dir]), 3);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }
}