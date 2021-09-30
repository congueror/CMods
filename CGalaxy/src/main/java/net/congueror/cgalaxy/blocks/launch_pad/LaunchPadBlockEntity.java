package net.congueror.cgalaxy.blocks.launch_pad;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.api.machine.tickable.AbstractTickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class LaunchPadBlockEntity extends AbstractTickableBlockEntity {
    public LaunchPadBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.LAUNCH_PAD.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void tick() {
        assert level != null;
        if (level.isClientSide) {
            return;
        }

        Block block = level.getBlockState(worldPosition).getBlock();
        if (block instanceof LaunchPadBlock padBlock) {
            if (padBlock.is3x3(worldPosition, level)) {
                level.setBlockAndUpdate(worldPosition, padBlock.defaultBlockState().setValue(BlockStateProperties.LIT, true));
            } else {
                level.setBlockAndUpdate(worldPosition, padBlock.defaultBlockState().setValue(BlockStateProperties.LIT, false));
            }
        }
    }
}
