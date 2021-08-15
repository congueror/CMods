package com.congueror.cgalaxy.block.fuel_loader;

import com.congueror.clib.blocks.block.AbstractFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FuelLoaderBlock extends AbstractFluidBlock {
    public FuelLoaderBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FuelLoaderTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FuelLoaderTileEntity) {
                FuelLoaderTileEntity te = (FuelLoaderTileEntity) tileEntity;
                this.blockRightClick(player, te);
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }
}
