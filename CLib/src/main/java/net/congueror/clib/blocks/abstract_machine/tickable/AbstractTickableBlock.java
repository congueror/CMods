package net.congueror.clib.blocks.abstract_machine.tickable;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractTickableBlock extends Block implements EntityBlock {
    public AbstractTickableBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @Nonnull BlockState pState, @Nonnull BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof AbstractTickableBlockEntity tile) {
                tile.tick();
            }
        };
    }
}
