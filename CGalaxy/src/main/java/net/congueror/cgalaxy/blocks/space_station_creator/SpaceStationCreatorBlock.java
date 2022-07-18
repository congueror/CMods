package net.congueror.cgalaxy.blocks.space_station_creator;

import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.clib.blocks.machine_base.machine.AbstractItemMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class SpaceStationCreatorBlock extends AbstractItemMachineBlock {
    public SpaceStationCreatorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SpaceStationCreatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction dir = context.getHorizontalDirection().getOpposite();
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (level.getBlockState(pos.relative(dir.getClockWise())).canBeReplaced(context)) {
            return super.getStateForPlacement(context);
        } else {
            return null;
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            Direction dir = pState.getValue(BlockStateProperties.HORIZONTAL_FACING);
            pLevel.setBlockAndUpdate(pPos.relative(dir.getClockWise()), CGBlockInit.SPACE_STATION_CREATOR_1.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir));
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof SpaceStationCreatorBlockEntity) {
                ((SpaceStationCreatorBlockEntity) tileentity).dropIngredients();
            }
            worldIn.setBlockAndUpdate(pos.relative(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise()), Blocks.AIR.defaultBlockState());
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SpaceStationCreatorBlockEntity te) {
                return this.blockRightClick(pPlayer, te);
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
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
