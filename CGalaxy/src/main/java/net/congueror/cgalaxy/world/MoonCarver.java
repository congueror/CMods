package net.congueror.cgalaxy.world;

import com.mojang.serialization.Codec;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class MoonCarver extends CaveWorldCarver {

    public MoonCarver(Codec<CaveCarverConfiguration> p_159194_) {
        super(p_159194_);
    }

    @Override
    protected boolean canReplaceBlock(BlockState state) {
        return state.is(CGBlockInit.MOON_STONE.get());
    }

    @Override
    protected boolean carveBlock(CarvingContext pContext, CaveCarverConfiguration pConfig, ChunkAccess pChunk, Function<BlockPos, Holder<Biome>> pBiomeAccessor, CarvingMask pCarvingMask, BlockPos.MutableBlockPos pPos, BlockPos.MutableBlockPos pCheckPos, Aquifer pAquifer, MutableBoolean pReachedSurface) {
        BlockState blockstate = pChunk.getBlockState(pPos);
        if (blockstate.is(CGBlockInit.MOON_REGOLITH.get())) {
            pReachedSurface.setTrue();
        }

        if (!this.canReplaceBlock(blockstate)) {
            return false;
        } else {
            BlockState blockstate1 = this.getCarveState(pContext, pConfig, pPos, pAquifer);
            if (blockstate1 == null) {
                return false;
            } else {
                pChunk.setBlockState(pPos, blockstate1, false);
                if (pAquifer.shouldScheduleFluidUpdate() && !blockstate1.getFluidState().isEmpty()) {
                    pChunk.markPosForPostprocessing(pPos);
                }

                if (pReachedSurface.isTrue()) {
                    pCheckPos.setWithOffset(pPos, Direction.DOWN);
                    if (pChunk.getBlockState(pCheckPos).is(Blocks.DIRT)) {
                        pContext.topMaterial(pBiomeAccessor, pChunk, pCheckPos, !blockstate1.getFluidState().isEmpty()).ifPresent((p_190743_) -> {
                            pChunk.setBlockState(pCheckPos, p_190743_, false);
                            if (!p_190743_.getFluidState().isEmpty()) {
                                pChunk.markPosForPostprocessing(pCheckPos);
                            }

                        });
                    }
                }

                return true;
            }
        }
        /*
        if (!this.canReplaceBlock(blockstate)) {
            return false;
        } else {
            BlockState blockstate2 = this.getCarveState(pContext, pConfig, pPos, pAquifer);
            if (blockstate2 == null) {
                return false;
            } else if (blockstate2.is(Blocks.LAVA)) {
                return false;
            } else {
                pChunk.setBlockState(pPos, blockstate2, false);
                if (pReachedSurface.isTrue()) {
                    pCheckPos.setWithOffset(pPos, Direction.DOWN);
                    if (pChunk.getBlockState(pCheckPos).is(Blocks.DIRT)) {
                        pChunk.setBlockState(pCheckPos, pBiomeAccessor.apply(pPos).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial(), false);
                    }
                }

                return true;
            }
        }*/
    }
}
