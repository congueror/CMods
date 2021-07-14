package com.congueror.cgalaxy.block;

import com.congueror.cgalaxy.entities.RocketEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LaunchPadBlock extends Block {

    public LaunchPadBlock(Properties properties) {
        super(properties);
    }

    /**
     * Checks whether the structure is complete.
     * @param pos Position of middle block
     * @param world World
     * @return true if complete, otherwise false.
     */
    public boolean is3x3(BlockPos pos, IBlockReader world) {
        return BlockPos.getAllInBox(pos.add(-1, 0, -1), pos.add(1, 0, 1)).allMatch(blockPos -> world.getBlockState(blockPos).getBlock().equals(this));
    }

    /**
     * Gets the rocket entity that is on top of the multiblock.
     * @param world World
     * @param pos Position of middle block
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
     * Spawns a rocket on top of the middle launch pad.
     * @param world World
     * @param pos Position of middle block
     * @param entity The rocket entity
     * @return true if no rocket is present and if the multiblock is complete, otherwise false.
     */
    public boolean spawnRocket(World world, BlockPos pos, RocketEntity entity) {
        if (this.is3x3(pos, world) && this.getRocket(world, pos) == null) {
            entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
            world.addEntity(entity);
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }
}