package com.congueror.cgalaxy.world.carver;

import com.congueror.cgalaxy.init.BlockInit;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class MoonCarver extends CaveWorldCarver {
    public MoonCarver(Codec<ProbabilityConfig> codec, int p_i231917_2_) {
        super(codec, p_i231917_2_);
    }

    @Override
    protected boolean canCarveBlock(BlockState state, BlockState aboveState) {
        return state.matchesBlock(BlockInit.MOON_STONE.get());
    }

    @Override
    protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> p_230358_2_, BitSet carvingMask, Random rand, BlockPos.Mutable p_230358_5_, BlockPos.Mutable p_230358_6_, BlockPos.Mutable p_230358_7_, int p_230358_8_, int p_230358_9_, int p_230358_10_, int posX, int posZ, int p_230358_13_, int posY, int p_230358_15_, MutableBoolean isSurface) {
        int i = p_230358_13_ | p_230358_15_ << 4 | posY << 8;
        if (carvingMask.get(i)) {
            return false;
        } else {
            carvingMask.set(i);
            p_230358_5_.setPos(posX, posY, posZ);
            BlockState blockstate = chunk.getBlockState(p_230358_5_);
            BlockState blockstate1 = chunk.getBlockState(p_230358_6_.setAndMove(p_230358_5_, Direction.UP));
            if (blockstate.matchesBlock(Blocks.GRASS_BLOCK) || blockstate.matchesBlock(Blocks.MYCELIUM)) {
                isSurface.setTrue();
            }

            if (!this.canCarveBlock(blockstate, blockstate1)) {
                return false;
            } else {
                chunk.setBlockState(p_230358_5_, CAVE_AIR, false);
                if (isSurface.isTrue()) {
                    p_230358_7_.setAndMove(p_230358_5_, Direction.DOWN);
                    if (chunk.getBlockState(p_230358_7_).matchesBlock(Blocks.DIRT)) {
                        chunk.setBlockState(p_230358_7_, p_230358_2_.apply(p_230358_5_).getGenerationSettings().getSurfaceBuilderConfig().getTop(), false);
                    }

                }

                return true;
            }
        }
    }
}

