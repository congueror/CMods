package com.congueror.clib.api.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Simple interface that merges {@link IInventory} and {@link IFluidHandler}. See {@link FluidRecipeWrapper} for more information.
 */
public interface IItemFluidInventory extends IInventory, IFluidHandler {
}
