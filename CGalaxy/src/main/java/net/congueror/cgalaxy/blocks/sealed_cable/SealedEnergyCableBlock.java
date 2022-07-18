package net.congueror.cgalaxy.blocks.sealed_cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SealedEnergyCableBlock extends SealedCableBlock {

    public SealedEnergyCableBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SealedEnergyCableBlockEntity(pPos, pState);
    }
}