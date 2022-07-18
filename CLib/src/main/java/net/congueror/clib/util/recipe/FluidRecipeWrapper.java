package net.congueror.clib.util.recipe;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

/**
 * Forked from {@link net.minecraftforge.items.wrapper.RecipeWrapper} to provide a way to merge the IInventory and IFluidHandler interfaces in a way that can be used by
 * tile entities without having to use the flawed {@link net.minecraft.world.Container} interface.
 */
public class FluidRecipeWrapper implements IItemFluidInventory {

    protected final IItemHandlerModifiable inv;
    protected final IFluidHandler handler;

    public FluidRecipeWrapper(IItemHandlerModifiable inv, IFluidHandler fluid) {
        this.inv = inv;
        this.handler = fluid;
    }

    /*
    =============================Container=============================
     */

    @Override
    public int getContainerSize() {
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

    @Nonnull
    @Override
    public ItemStack getItem(int index) {
        return inv.getStackInSlot(index);
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = inv.getStackInSlot(index);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack s = getItem(index);
        if(s.isEmpty()) return ItemStack.EMPTY;
        setItem(index, ItemStack.EMPTY);
        return s;
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        inv.setStackInSlot(index, stack);
    }

    @Override
    public void clearContent() {
        for(int i = 0; i < inv.getSlots(); i++)
        {
            inv.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean canPlaceItem(int index, @Nonnull ItemStack stack) {
        return inv.isItemValid(index, stack);
    }

    @Override
    public int getMaxStackSize() {return 0;}
    @Override
    public void setChanged() {}
    @Override
    public boolean stillValid(@Nonnull Player player) {return false;}
    @Override
    public void startOpen(@Nonnull Player player) {}
    @Override
    public void stopOpen(@Nonnull Player player) {}

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
