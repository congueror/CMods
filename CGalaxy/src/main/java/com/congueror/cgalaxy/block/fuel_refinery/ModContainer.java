package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.clib.util.ModEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class ModContainer<T extends FuelRefineryTileEntity> extends Container {
    //TODO
    T tile;
    IIntArray data;
    IItemHandler playerInventory;

    public ModContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, T tile, IIntArray dataIn) {
        super(type, id);
        this.tile = tile;
        this.data = dataIn;
        this.playerInventory = new InvWrapper(playerInventory);

        if (tile != null) {
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                addSlot(new SlotItemHandler(iItemHandler, 0, 51, 18));
                addSlot(new SlotItemHandler(iItemHandler, 1, 126, 18));
                addSlot(new SlotItemHandler(iItemHandler, 2, 4, 4));
                addSlot(new SlotItemHandler(iItemHandler, 3, 4, 22));
                addSlot(new SlotItemHandler(iItemHandler, 4, 4, 40));
                addSlot(new SlotItemHandler(iItemHandler, 5, 4, 58));
            });
        }

        trackPower();
        trackIntArray(tile.data);
        layoutPlayerInventorySlots(28, 84);
    }

    public int getProgress() {
        return tile.data.get(0);
    }

    public int getProcessTime() {
        return tile.data.get(1);
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount,
                             int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (tile.isItemValid(stack)) {
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

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerIn, tile.getBlockState().getBlock());
    }

    public int getEnergy() {
        return tile.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    // Setup syncing of power from server to client so that the GUI can show the
    // amount of power in the block
    private void trackPower() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so
        // we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tile.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((ModEnergyStorage) h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tile.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((ModEnergyStorage) h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }
}
