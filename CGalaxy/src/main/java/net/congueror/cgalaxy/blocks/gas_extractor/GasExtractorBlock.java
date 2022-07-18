package net.congueror.cgalaxy.blocks.gas_extractor;

import net.congueror.clib.blocks.machine_base.machine.AbstractFluidMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GasExtractorBlock extends AbstractFluidMachineBlock {
    public GasExtractorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
        return new GasExtractorBlockEntity(pPos, pState);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level levelIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult hit) {
        if (!levelIn.isClientSide) {
            BlockEntity blockEntity = levelIn.getBlockEntity(pos);
            if (blockEntity instanceof GasExtractorBlockEntity te) {
                return this.blockRightClick(player, te);
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}
