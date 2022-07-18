package net.congueror.clib.util.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Used to modify the onPlace method. See {@link Pre} and {@link Post}
 */
public abstract class BlockOnPlacedEvent extends Event {//TODO: Better docs
    private final Level level;
    private final BlockPos pos;
    /**
     * The previous blockstate that existed in that position
     */
    private final BlockState oldState;
    private final boolean isMoving;
    /**
     * The block that was placed down.
     */
    private final Block newBlock;

    public BlockOnPlacedEvent(Level level, BlockPos pos, Block newBlock, BlockState oldState, boolean isMoving) {
        this.level = level;
        this.pos = pos;
        this.oldState = oldState;
        this.isMoving = isMoving;
        this.newBlock = newBlock;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getOldState() {
        return oldState;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Block getNewBlock() {
        return newBlock;
    }

    /**
     * Called before the onPlaced method of a block is executed. Cancelling this event will not cancel the placement of the block but the onPlace method.
     */
    @Cancelable
    public static class Pre extends BlockOnPlacedEvent {

        public Pre(Level level, BlockPos pos, Block newBlock, BlockState oldState, boolean isMoving) {
            super(level, pos, newBlock, oldState, isMoving);
        }
    }


    /**
     * Called after the onPlaced method is executed.
     */
    public static class Post extends BlockOnPlacedEvent {

        public Post(Level level, BlockPos pos, Block newBlock, BlockState oldState, boolean isMoving) {
            super(level, pos, newBlock, oldState, isMoving);
        }
    }
}
