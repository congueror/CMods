package net.congueror.cgalaxy.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AdvancedDoorBlock extends DoorBlock {

    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 9.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape WEST_AABB = Block.box(7.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(6.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);

    public AdvancedDoorBlock(Properties p_52737_) {
        super(p_52737_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        switch(direction) {
            case EAST:
            default:
                return EAST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case NORTH:
                return NORTH_AABB;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(OPEN) ? Shapes.empty() : getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        pState = pState.cycle(OPEN);
        pLevel.setBlock(pPos, pState, 10);
        pLevel.levelEvent(pPlayer, pState.getValue(OPEN) ? 1005 : 1011, pPos, 0);
        pLevel.gameEvent(pPlayer, this.isOpen(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
