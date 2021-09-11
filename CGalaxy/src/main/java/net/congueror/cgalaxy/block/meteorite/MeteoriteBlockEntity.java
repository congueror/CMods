package net.congueror.cgalaxy.block.meteorite;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.api.objects.machine_objects.tickable.AbstractTickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.world.level.block.FallingBlock.isFree;

public class MeteoriteBlockEntity extends AbstractTickableBlockEntity {
    int time;

    public MeteoriteBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.METEORITE.get(), pWorldPosition, pBlockState);
    }


    @Override
    public void tick() {
        assert level != null;
        if (isFree(level.getBlockState(worldPosition.below())) && worldPosition.getY() >= 0) {
            FallingBlockEntity fallingblockentity = new FallingBlockEntity(level, (double)worldPosition.getX() + 0.5D, worldPosition.getY(), (double)worldPosition.getZ() + 0.5D, level.getBlockState(worldPosition));
            level.addFreshEntity(fallingblockentity);
        }

        if (getBlockState().getValue(BlockStateProperties.LIT)) {
            BlockPos pos = getBlockPos();
            assert level != null;
            if (level.isClientSide) {
                Random random = new Random();
                level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + 0.5D, (double) pos.getY() + random.nextDouble() + random.nextDouble(), (double) pos.getZ() + 0.5D, 0.0D, 0.07D, 0.0D);
            } else {
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
