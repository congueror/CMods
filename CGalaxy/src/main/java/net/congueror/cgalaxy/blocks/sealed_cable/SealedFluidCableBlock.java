package net.congueror.cgalaxy.blocks.sealed_cable;

import net.congueror.clib.items.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SealedFluidCableBlock extends SealedCableBlock {
    public SealedFluidCableBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (stack.getItem() instanceof BlockItem bi && blockEntity instanceof SealedFluidCableBlockEntity be) {
                if (Block.byItem(bi).defaultBlockState().isCollisionShapeFullBlock(pLevel, pPos)) {
                    be.setBlock(Block.byItem(bi));
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return WrenchItem.wrenchUse(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SealedFluidCableBlockEntity(pPos, pState);
    }
}
