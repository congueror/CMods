package net.congueror.clib.blocks.generic;

import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ToolAction;

import java.util.function.Supplier;

/**
 * An interface which can be implemented by a Block class to add additional functionality for the {@link BlockBuilder}. See {@link CLBlock} for example on how to implement properly
 */
public interface ICLibBlock {
    void setModifiedState(ToolAction action, Supplier<? extends Block> block);
}
