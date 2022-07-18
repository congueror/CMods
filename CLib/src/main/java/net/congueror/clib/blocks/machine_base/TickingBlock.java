package net.congueror.clib.blocks.machine_base;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface TickingBlock extends EntityBlock {

    @Nullable
    @Override
    default <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof TickingBlockEntity tile) {
                if (pLevel1.isClientSide)
                    tile.clientTick();
                tile.tick();
            }
        };
    }
}
