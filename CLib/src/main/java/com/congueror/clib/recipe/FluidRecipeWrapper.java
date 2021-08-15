package com.congueror.clib.recipe;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

/**
 * Forked from {@link net.minecraftforge.items.wrapper.RecipeWrapper} to provide a way to merge the IInventory and IFluidHandler interfaces in a way that can be used by
 * tile entities without having to use the flawed {@link net.minecraft.inventory.IInventory} interface.
 */
public class FluidRecipeWrapper implements IItemFluidInventory {

    protected final IItemHandlerModifiable inv;
    protected final IFluidHandler handler;

    public FluidRecipeWrapper(IItemHandlerModifiable inv, IFluidHandler fluid) {
        this.inv = inv;
        this.handler = fluid;
    }

    /*
    =============================IInventory=============================
     */

    @Override
    public int getSizeInventory() {
        return inv.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            if(!inv.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inv.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inv.getStackInSlot(index);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack s = getStackInSlot(index);
        if(s.isEmpty()) return ItemStack.EMPTY;
        setInventorySlotContents(index, ItemStack.EMPTY);
        return s;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inv.setStackInSlot(index, stack);
    }

    @Override
    public void clear() {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            inv.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return inv.isItemValid(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {return 0;}
    @Override
    public void markDirty() {}
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {return false;}
    @Override
    public void openInventory(PlayerEntity player) {}
    @Override
    public void closeInventory(PlayerEntity player) {}

    /*
    =============================IFluidHandler=============================
     */

    @Override
    public int getTanks() {
        return handler.getTanks();
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return handler.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return handler.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return handler.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return handler.fill(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return handler.drain(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return handler.drain(maxDrain, action);
    }
}
