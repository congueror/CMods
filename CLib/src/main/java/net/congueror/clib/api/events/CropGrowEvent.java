package net.congueror.clib.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;

import java.util.Random;

@Event.HasResult
public class CropGrowEvent extends Event {

    BlockState state;
    Level level;
    BlockPos pos;
    Random random;

    public CropGrowEvent(BlockState state, Level level, BlockPos pos, Random random) {
        this.state = state;
        this.level = level;
        this.pos = pos;
        this.random = random;
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
