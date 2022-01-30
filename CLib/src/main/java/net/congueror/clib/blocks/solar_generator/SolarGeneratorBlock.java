package net.congueror.clib.blocks.solar_generator;

import net.congueror.clib.blocks.abstract_machine.item.AbstractItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class SolarGeneratorBlock extends AbstractItemBlock {
    private static int tiers = 0;
    private final int generation;
    private final int tier;

    public SolarGeneratorBlock(Properties p_49795_, int generation, int tier) {
        super(p_49795_);
        this.generation = generation;

        if (tier == 0) {
            this.tier = 1000;
        } else if (tier - 1 == tiers) {
            this.tier = tier;
            tiers++;
        } else {
            this.tier = tiers + 1;
            tiers++;
        }
    }

    public int getGeneration() {
        return generation;
    }

    public int getTier() {
        return tier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SolarGeneratorBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SolarGeneratorBlockEntity te) {
                return this.blockRightClick(pPlayer, te);
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}