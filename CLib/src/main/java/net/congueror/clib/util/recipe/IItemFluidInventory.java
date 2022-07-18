package net.congueror.clib.util.recipe;

import net.minecraft.world.Container;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Simple interface that merges {@link Container} and {@link IFluidHandler}. See {@link FluidRecipeWrapper} for more information.
 */
public interface IItemFluidInventory extends Container, IFluidHandler {
}
