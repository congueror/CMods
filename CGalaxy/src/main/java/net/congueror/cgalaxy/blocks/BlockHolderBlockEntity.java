package net.congueror.cgalaxy.blocks;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockHolderBlockEntity extends BlockEntity {
    int index;

    public BlockHolderBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.BLOCK_HOLDER.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        index = pTag.getInt("Index");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Index", index);
    }
}
