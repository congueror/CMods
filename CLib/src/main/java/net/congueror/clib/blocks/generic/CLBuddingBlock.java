package net.congueror.clib.blocks.generic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class CLBuddingBlock extends AmethystBlock {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    private final List<Supplier<? extends Block>> list = new ArrayList<>();

    public CLBuddingBlock(Properties properties, Supplier<? extends Block> small, Supplier<? extends Block> medium, Supplier<? extends Block> large, Supplier<? extends Block> cluster) {
        super(properties.randomTicks());
        list.add(0, small);
        list.add(1, medium);
        list.add(2, large);
        list.add(3, cluster);
    }

    @Nonnull
    @Override
    public PushReaction getPistonPushReaction(@Nonnull BlockState pState) {
        return PushReaction.DESTROY;
    }

    @Override
    public void randomTick(@Nonnull BlockState pState, @Nonnull ServerLevel pLevel, @Nonnull BlockPos pPos, Random pRandom) {
        if (pRandom.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            Block block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = list.get(0).get();
            } else if (blockstate.is(list.get(0).get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = list.get(1).get();
            } else if (blockstate.is(list.get(1).get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = list.get(2).get();
            } else if (blockstate.is(list.get(2).get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = list.get(3).get();
            }

            if (block != null) {
                BlockState blockstate1 = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
                pLevel.setBlockAndUpdate(blockpos, blockstate1);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER) && pState.getFluidState().getAmount() == 8;
    }
}
