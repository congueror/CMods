package net.congueror.cgalaxy.blocks.space_station_creator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SpaceStationCreatorSecondBlock extends Block {
    public SpaceStationCreatorSecondBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            pLevel.setBlockAndUpdate(pPos.relative(pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getCounterClockWise()), Blocks.AIR.defaultBlockState());
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos.relative(pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getCounterClockWise()));
            if (blockEntity instanceof SpaceStationCreatorBlockEntity te) {
                NetworkHooks.openGui((ServerPlayer) pPlayer, te, te.getBlockPos());
                return InteractionResult.SUCCESS;
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}
