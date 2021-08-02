package com.congueror.cgalaxy.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Simply extends IInventory and IFluidHandler so it can be used by recipes in a clean way.
 */
public interface IFluidInv extends IInventory, IFluidHandler {
}
