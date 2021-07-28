package com.congueror.cgalaxy.block.fuel_loader;

import com.congueror.cgalaxy.init.ContainerInit;
import com.congueror.clib.blocks.AbstractFluidContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FuelLoaderContainer extends AbstractFluidContainer<FuelLoaderTileEntity> {
    FuelLoaderTileEntity te;

    public FuelLoaderContainer(int id, PlayerInventory playerInventory, FuelLoaderTileEntity tile, IIntArray dataIn) {
        super(ContainerInit.FUEL_LOADER.get(), id, playerInventory, tile, dataIn);
        this.te = tile;

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 120, 18));
            addSlot(new SlotItemHandler(iItemHandler, 1, 4, 4));
            addSlot(new SlotItemHandler(iItemHandler, 2, 4, 22));
            addSlot(new SlotItemHandler(iItemHandler, 3, 4, 40));
            addSlot(new SlotItemHandler(iItemHandler, 4, 4, 58));
        });

        trackIntArray(te.data);
    }


    @Override
    public FluidTank[] getFluidTanks() {
        return te.tanks;
    }

    @Override
    public int getEnergyUsage() {
        return te.getEnergyUsage();
    }

    public int getMaxEnergy() {
        return te.getEnergyCapacity();
    }

    public int getProgress() {
        return te.data.get(0);
    }

    public int getProcessTime() {
        return te.data.get(1);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;//TODO: fix plz
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (index < te.invSize().length) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (te.canItemFit(index, stack)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
