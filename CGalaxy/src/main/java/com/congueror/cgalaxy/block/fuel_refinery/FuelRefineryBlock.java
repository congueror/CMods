package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.clib.api.machines.fluid_machine.AbstractFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class FuelRefineryBlock extends AbstractFluidBlock {

    public FuelRefineryBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FuelRefineryTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FuelRefineryTileEntity) {
                FuelRefineryTileEntity te = (FuelRefineryTileEntity) tileEntity;
                this.blockRightClick(player, te);
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(BlockStateProperties.LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F,
                        false);//TODO: Custom sounds
            }

            Direction direction = stateIn.get(BlockStateProperties.HORIZONTAL_FACING);
            double next = rand.nextDouble();
            double f0 = 0;
            double f1 = 0.1D + (next < 0.7 ? next : 0);
            double f2 = 0;
            if (direction.equals(Direction.EAST)) {
                f0 = 0.5;
            } else if (direction.equals(Direction.WEST)) {
                f0 = -0.5;
            } else if (direction.equals(Direction.SOUTH)) {
                f2 = 0.5;
            } else if (direction.equals(Direction.NORTH)) {
                f2 = -0.5;
            }
            worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, d0 + f0, d1 + f1, d2 + f2, 0.0D, 0.0D, 0.0D);
        }
    }
}
