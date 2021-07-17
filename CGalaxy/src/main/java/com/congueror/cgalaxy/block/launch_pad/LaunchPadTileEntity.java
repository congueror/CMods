package com.congueror.cgalaxy.block.launch_pad;

import com.congueror.cgalaxy.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class LaunchPadTileEntity extends TileEntity implements ITickableTileEntity {
    public LaunchPadTileEntity() {
        super(TileEntityInit.LAUNCH_PAD.get());
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof LaunchPadBlock) {
            LaunchPadBlock launchPadBlock = (LaunchPadBlock) block;
            if (launchPadBlock.is3x3(pos, world)) {
                world.setBlockState(pos, launchPadBlock.getDefaultState().with(BlockStateProperties.LIT, true));
            } else {
                world.setBlockState(pos, launchPadBlock.getDefaultState().with(BlockStateProperties.LIT, false));
            }
        }
    }
}
