package net.congueror.cgalaxy.block.meteorite;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.api.machine.tickable.AbstractTickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;
import java.util.Random;

public class MeteoriteBlockEntity extends AbstractTickableBlockEntity {
    int time;

    public MeteoriteBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.METEORITE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void tick() {
        if (getBlockState().getValue(BlockStateProperties.LIT)) {
            BlockPos pos = getBlockPos();
            assert level != null;
            Random random = level.getRandom();
            if (level.isClientSide) {
                if (random.nextDouble() < 0.3d) {
                    level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, 0.0D, 0.1D, 0.0D);
                }
            }
            time++;
            setChanged();
            if (time >= 2400) {
                time = 0;
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
