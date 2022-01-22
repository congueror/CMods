package net.congueror.cgalaxy.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class CoalTorchBlock extends TorchBlock {
    public CoalTorchBlock(Properties pProperties) {
        super(pProperties, ParticleTypes.FLAME);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
    }
}
