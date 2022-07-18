package net.congueror.cgalaxy.world.features;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import java.util.Set;

public class MeteoriteFeature extends Feature<BlockStateConfiguration> {
    public MeteoriteFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> pContext) {
        WorldGenLevel reader = pContext.level();
        BlockPos pos = pContext.origin();
        BlockStateConfiguration config = pContext.config();
        Level world = reader.getLevel();
        int size = pContext.random().nextInt(5);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        Set<BlockPos> set = Sets.newHashSet();
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float) j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float) k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float) l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        float f = size * (0.7F + world.random.nextFloat() * 0.6F);
                        double d4 = x;
                        double d6 = y;
                        double d8 = z;

                        for (; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            if (reader.getBlockState(blockpos).canOcclude()) {
                                set.add(blockpos);
                            }

                            d4 += d0 * (double) 0.3F;
                            d6 += d1 * (double) 0.3F;
                            d8 += d2 * (double) 0.3F;
                        }
                    }
                }
            }
        }

        for (BlockPos pos1 : set) {
            reader.setBlock(pos1, Blocks.AIR.defaultBlockState(), 4);
            reader.setBlock(pos.below(size + 2), config.state, 4);
        }

        return true;
    }
}
