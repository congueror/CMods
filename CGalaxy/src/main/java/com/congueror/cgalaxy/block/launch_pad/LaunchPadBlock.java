package com.congueror.cgalaxy.block.launch_pad;

import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.clib.CLib;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LaunchPadBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape SHAPE_MID = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public LaunchPadBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(BlockStateProperties.LIT, false));
    }

    /**
     * Checks whether the structure is complete.
     *
     * @param pos   Position of middle block
     * @param world World
     * @return true if complete, otherwise false.
     */
    public boolean is3x3(BlockPos pos, IBlockReader world) {
        return BlockPos.getAllInBox(pos.add(-1, 0, -1), pos.add(1, 0, 1)).allMatch(blockPos -> world.getBlockState(blockPos).getBlock().equals(this));
    }

    /**
     * Gets the rocket entity that is on top of the multiblock.
     *
     * @param world World
     * @param pos   Position of middle block
     * @return rocket entity if found, otherwise null.
     */
    public RocketEntity getRocket(IWorld world, BlockPos pos) {
        List<Entity> rocket = world.getEntitiesWithinAABB(RocketEntity.class, new AxisAlignedBB(
                new BlockPos(pos.getX() - 1, pos.getY() + 1, pos.getZ() - 1),
                new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)));
        for (Entity entity : rocket) {
            if (entity instanceof RocketEntity) {
                return (RocketEntity) (entity);
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
    public BlockPos getMidBlock(IWorld world, BlockPos pos) {
        ArrayList<BlockPos> pos1 = new ArrayList<>();
        pos1.add(pos.east());
        pos1.add(pos.west());
        pos1.add(pos.south());
        pos1.add(pos.north());
        pos1.add(pos);
        pos1.add(pos.add(1, 0, 1));
        pos1.add(pos.add(-1, 0, 1));
        pos1.add(pos.add(-1, 0, -1));
        pos1.add(pos.add(1, 0, -1));
        for (BlockPos pos2 : pos1) {
            if (world.getBlockState(pos2).matchesBlock(BlockInit.LAUNCH_PAD.get())) {
                if (world.getBlockState(pos2).get(BlockStateProperties.LIT)) {
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
    public boolean spawnRocket(World world, BlockPos pos, RocketEntity entity, int fuel) {
        if (this.is3x3(pos, world) && this.getRocket(world, pos) == null) {
            entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
            world.addEntity(entity);
            entity.fill(fuel);
            return true;
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(BlockStateProperties.LIT)) {
            return SHAPE_MID;
        } else {
            return SHAPE;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LaunchPadTileEntity();
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }
}