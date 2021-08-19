package com.congueror.cgalaxy.world;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class MeteoriteFeature extends Feature<BlockStateFeatureConfig> {
    public MeteoriteFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        while (true) {
            World world = reader.getWorld();
            int size = rand.nextInt(5);
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();


            Set<BlockPos> set = Sets.newHashSet();
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    for (int l = 0; l < 16; ++l) {
                        if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                            double d0 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
                            double d1 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
                            double d2 = (double) ((float) l / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 = d0 / d3;
                            d1 = d1 / d3;
                            d2 = d2 / d3;
                            float f = size * (0.7F + world.rand.nextFloat() * 0.6F);
                            double d4 = x;
                            double d6 = y;
                            double d8 = z;

                            for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                                BlockPos blockpos = new BlockPos(d4, d6, d8);
                                BlockState blockstate = world.getBlockState(blockpos);
                                FluidState fluidstate = world.getFluidState(blockpos);
                                set.add(blockpos);

                                d4 += d0 * (double) 0.3F;
                                d6 += d1 * (double) 0.3F;
                                d8 += d2 * (double) 0.3F;
                            }
                        }
                    }
                }
            }

            for (BlockPos pos1 : set) {
                reader.setBlockState(pos1, Blocks.AIR.getDefaultState(), 4);
            }

            return true;
        }
    }
}
