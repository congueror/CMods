package net.congueror.clib.util.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;

import java.util.Random;

@Event.HasResult
public class SaplingAdvanceEvent extends Event {
    Level level;
    BlockState state;
    BlockPos pos;
    Random rand;
    AbstractTreeGrower treeGrower;

    public SaplingAdvanceEvent(Level level, BlockState state, BlockPos pos, Random rand, AbstractTreeGrower treeGrower) {
        this.level = level;
        this.state = state;
        this.pos = pos;
        this.rand = rand;
        this.treeGrower = treeGrower;
    }

    public Level getLevel() {
        return level;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Random getRand() {
        return rand;
    }

    public AbstractTreeGrower getTreeGrower() {
        return treeGrower;
    }
}
