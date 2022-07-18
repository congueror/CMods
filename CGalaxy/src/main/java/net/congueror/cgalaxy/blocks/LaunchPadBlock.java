package net.congueror.cgalaxy.blocks;

import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LaunchPadBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape SHAPE_MID = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public LaunchPadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.LIT, false).setValue(BlockStateProperties.ATTACHED, false));
    }

    /**
     * Checks whether the structure is complete.
     *
     * @param pos    Position of middle block
     * @param getter World
     * @return true if complete, otherwise false.
     */
    public boolean is3x3(BlockPos pos, BlockGetter getter) {
        BlockState middle = getter.getBlockState(pos);
        if (middle.is(this)) {
            return BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, 0, 1)).allMatch(blockPos -> {
                BlockState ye = getter.getBlockState(blockPos);
                if (ye.is(this)) {
                    if (ye.getValue(BlockStateProperties.ATTACHED)) {
                        return getMidBlock(getter, blockPos) == null || blockPos.equals(pos);
                    }
                    return true;
                }
                return false;
            });
        }
        return false;
    }

    /**
     * Gets the rocket entity that is on top of the multiblock.
     *
     * @param level World
     * @param pos   Position of middle block
     * @return rocket entity if found, otherwise null.
     */
    public AbstractRocket getRocket(LevelAccessor level, BlockPos pos) {
        List<AbstractRocket> rocket = level.getEntitiesOfClass(AbstractRocket.class, new AABB(
                new BlockPos(pos.getX() - 1, pos.getY() + 1, pos.getZ() - 1),
                new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)));
        for (AbstractRocket entity : rocket) {
            if (entity != null) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Gets the middle block of the multiblock.
     *
     * @param world The world
     * @param pos   Current Position
     * @return block position of the middle block, null otherwise.
     */
    public BlockPos getMidBlock(BlockGetter world, BlockPos pos) {
        ArrayList<BlockPos> pos1 = getSurroundingBlocks(pos);
        for (BlockPos pos2 : pos1) {
            if (world.getBlockState(pos2).is(CGBlockInit.LAUNCH_PAD.get())) {
                if (world.getBlockState(pos2).getValue(BlockStateProperties.LIT)) {
                    return pos2;
                }
            }
        }
        return null;
    }

    /**
     * Gets all the surrounding block positions of a block position.
     *
     * @param pos Block position
     * @return A list containing the surrounding block positions of the given block pos
     */
    public ArrayList<BlockPos> getSurroundingBlocks(BlockPos pos) {
        ArrayList<BlockPos> pos1 = new ArrayList<>();
        pos1.add(pos.east());
        pos1.add(pos.west());
        pos1.add(pos.south());
        pos1.add(pos.north());
        pos1.add(pos);
        pos1.add(pos.offset(1, 0, 1));
        pos1.add(pos.offset(-1, 0, 1));
        pos1.add(pos.offset(-1, 0, -1));
        pos1.add(pos.offset(1, 0, -1));
        return pos1;
    }

    /**
     * Spawns a rocket on top of the middle launch pad.
     *
     * @param world  World
     * @param pos    Position of middle block
     * @param entity The rocket entity
     * @return true if the rocket spawns successfully, otherwise false.
     */
    public boolean spawnRocket(Level world, BlockPos pos, AbstractRocket entity, int fuel, float rotation) {
        if (this.is3x3(pos, world) && this.getRocket(world, pos) == null) {
            entity.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
            entity.setYRot(rotation);
            world.addFreshEntity(entity);
            entity.fill(fuel);
            return true;
        }
        return false;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (this.is3x3(pPos, pLevel)) {
            if (!pState.getValue(BlockStateProperties.LIT))
                pLevel.setBlockAndUpdate(pPos, pState.setValue(BlockStateProperties.LIT, true).setValue(BlockStateProperties.ATTACHED, true));
            BlockPos.betweenClosedStream(pPos.offset(-1, 0, -1), pPos.offset(1, 0, 1)).forEach(blockPos -> {
                if (!pLevel.getBlockState(blockPos).getValue(BlockStateProperties.ATTACHED))
                    pLevel.setBlockAndUpdate(blockPos, defaultBlockState().setValue(BlockStateProperties.ATTACHED, true));
            });
        } else {
            getSurroundingBlocks(pPos).forEach(blockPos -> {
                if (this.is3x3(blockPos, pLevel)) {
                    if (!pState.getValue(BlockStateProperties.LIT))
                        pLevel.setBlockAndUpdate(blockPos, pState.setValue(BlockStateProperties.LIT, true).setValue(BlockStateProperties.ATTACHED, true));
                    BlockPos.betweenClosedStream(blockPos.offset(-1, 0, -1), blockPos.offset(1, 0, 1)).forEach(blockPos1 -> {
                        if (!pLevel.getBlockState(blockPos1).getValue(BlockStateProperties.ATTACHED))
                            pLevel.setBlockAndUpdate(blockPos1, defaultBlockState().setValue(BlockStateProperties.ATTACHED, true));
                    });
                }
            });
        }

        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getValue(BlockStateProperties.LIT)) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(BlockStateProperties.LIT, false).setValue(BlockStateProperties.ATTACHED, false));
            BlockPos.betweenClosedStream(pPos.offset(-1, 0, -1), pPos.offset(1, 0, 1)).forEach(blockPos1 -> {
                if (pLevel.getBlockState(blockPos1).is(this) && pLevel.getBlockState(blockPos1).getValue(BlockStateProperties.ATTACHED))
                    pLevel.setBlockAndUpdate(blockPos1, defaultBlockState().setValue(BlockStateProperties.ATTACHED, false));
            });
        } else if (pState.getValue(BlockStateProperties.ATTACHED)) {
            getSurroundingBlocks(pPos).forEach(blockPos -> {
                BlockState state = pLevel.getBlockState(blockPos);
                if (state.is(this) && state.getValue(BlockStateProperties.LIT)) {
                    pLevel.setBlockAndUpdate(blockPos, state.setValue(BlockStateProperties.LIT, false).setValue(BlockStateProperties.ATTACHED, false));
                    BlockPos.betweenClosedStream(blockPos.offset(-1, 0, -1), blockPos.offset(1, 0, 1)).forEach(blockPos1 -> {
                        if (pLevel.getBlockState(blockPos1).is(this) && pLevel.getBlockState(blockPos1).getValue(BlockStateProperties.ATTACHED))
                            pLevel.setBlockAndUpdate(blockPos1, defaultBlockState().setValue(BlockStateProperties.ATTACHED, false));
                    });
                }
            });
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        if (pState.getValue(BlockStateProperties.LIT)) {
            return SHAPE_MID;
        } else {
            return SHAPE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.LIT);
        pBuilder.add(BlockStateProperties.ATTACHED);
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter world, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }
}
