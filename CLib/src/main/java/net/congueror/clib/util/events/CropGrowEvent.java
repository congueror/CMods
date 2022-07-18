package net.congueror.clib.util.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;

import java.util.Random;

@Event.HasResult
public class CropGrowEvent extends Event {
    CropBlock block;
    BlockState state;
    Level level;
    BlockPos pos;
    Random random;

    public CropGrowEvent(CropBlock block, BlockState state, Level level, BlockPos pos, Random random) {
        this.block = block;
        this.state = state;
        this.level = level;
        this.pos = pos;
        this.random = random;
    }

    public CropBlock getBlock() {
        return block;
    }

    public BlockState getState() {
        return state;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Random getRandom() {
        return random;
    }
}
