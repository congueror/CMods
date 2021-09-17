package net.congueror.cgalaxy.block.fuel_refinery;


import net.congueror.cgalaxy.block.fuel_loader.FuelLoaderBlockEntity;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class FuelRefineryBlock extends AbstractFluidBlock {
    public FuelRefineryBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
        return new FuelRefineryBlockEntity(pPos, pState);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FuelRefineryBlockEntity te) {
                return this.blockRightClick(pPlayer, te);
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState stateIn, @Nonnull Level levelIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (stateIn.getValue(BlockStateProperties.LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                levelIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F,
                        false);//TODO: Custom sounds
            }

            Direction direction = stateIn.getValue(BlockStateProperties.HORIZONTAL_FACING);
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
            levelIn.addParticle(DustParticleOptions.REDSTONE, d0 + f0, d1 + f1, d2 + f2, 0.0D, 0.0D, 0.0D);
        }
    }
}
