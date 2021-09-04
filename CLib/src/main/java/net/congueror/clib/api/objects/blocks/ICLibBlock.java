package net.congueror.clib.api.objects.blocks;

import net.congueror.clib.api.objects.items.CLItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import java.util.function.Supplier;

/**
 * An interface which can be implemented by a Block class to add additional functionality for the {@link net.congueror.clib.api.registry.BlockBuilder}. See {@link CLBlock} for example on how to implement properly
 */
public interface ICLibBlock {
    void setModifiedState(ToolAction action, Supplier<? extends Block> block);
}
