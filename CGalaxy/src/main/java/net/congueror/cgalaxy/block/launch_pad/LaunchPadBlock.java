package net.congueror.cgalaxy.block.launch_pad;

import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.clib.api.objects.machine_objects.tickable.AbstractTickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LaunchPadBlock extends AbstractTickableBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape SHAPE_MID = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public LaunchPadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.LIT, false));
    }

    /**
     * Checks whether the structure is complete.
     *
     * @param pos   Position of middle block
     * @param getter World
     * @return true if complete, otherwise false.
     */
    public boolean is3x3(BlockPos pos, BlockGetter getter) {
        return BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, 0, 1)).allMatch(blockPos -> getter.getBlockState(blockPos).getBlock().equals(this));
    }

    /**
     * Gets the rocket entity that is on top of the multiblock.
     *
     * @param level World
     * @param pos   Position of middle block
     * @return rocket entity if found, otherwise null.
     */
    public RocketEntity getRocket(LevelAccessor level, BlockPos pos) {
        List<RocketEntity> rocket = level.getEntitiesOfClass(RocketEntity.class, new AABB(
                new BlockPos(pos.getX() - 1, pos.getY() + 1, pos.getZ() - 1),
                new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)));
        for (RocketEntity entity : rocket) {
            if (entity != null) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Gets the middle block of the multiblock.
     * @param world The world
     * @param pos   Current Position
     * @return block position of the middle block, null otherwise.
     */
    public BlockPos getMidBlock(LevelAccessor world, BlockPos pos) {
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
     * Spawns a rocket on top of the middle launch pad.
     *
     * @param world  World
     * @param pos    Position of middle block
     * @param entity The rocket entity
     * @return true if no rocket is present and if the multiblock is complete, otherwise false.
     */
    public boolean spawnRocket(Level world, BlockPos pos, RocketEntity entity, int fuel) {
        if (this.is3x3(pos, world) && this.getRocket(world, pos) == null) {
            entity.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
            world.addFreshEntity(entity);
            entity.fill(fuel);
            return true;
        }
        return false;
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
        return new LaunchPadBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.LIT);
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, BlockGetter world, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }
}
