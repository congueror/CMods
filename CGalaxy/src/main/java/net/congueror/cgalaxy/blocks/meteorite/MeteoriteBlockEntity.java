package net.congueror.cgalaxy.blocks.meteorite;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.api.machine.tickable.AbstractTickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;

public class MeteoriteBlockEntity extends AbstractTickableBlockEntity {
    int time;

    public MeteoriteBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.METEORITE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void tick() {
        if (getBlockState().getValue(BlockStateProperties.LIT)) {
            time++;
            setChanged();
            if (time >= 1800) {
                time = 0;
                assert level != null;
                BlockState oldState = level.getBlockState(worldPosition);
                level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                level.sendBlockUpdated(worldPosition, oldState, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                setChanged();
            }
        }
    }

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag pTag) {
        pTag.putInt("time", time);
        return super.save(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        time = pTag.getInt("time");
        super.load(pTag);
    }
}
