package net.congueror.cgalaxy.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class CoalWallTorchBlock extends WallTorchBlock {
    public CoalWallTorchBlock(Properties p_58123_) {
        super(p_58123_, ParticleTypes.SMOKE);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
    }
}
