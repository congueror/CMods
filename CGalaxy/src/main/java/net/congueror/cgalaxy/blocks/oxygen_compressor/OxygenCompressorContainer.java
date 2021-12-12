package net.congueror.cgalaxy.blocks.oxygen_compressor;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.clib.api.machine.fluid.AbstractFluidContainer;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class OxygenCompressorContainer extends AbstractFluidContainer<OxygenCompressorBlockEntity> {
    OxygenCompressorBlockEntity te;

    public OxygenCompressorContainer(int id, Player player, Inventory playerInventory, OxygenCompressorBlockEntity tile, ContainerData dataIn) {
        super(CGContainerInit.OXYGEN_COMPRESSOR.get(), id, player, playerInventory, tile, dataIn);
        this.te = tile;
        if (fluidLastTick.isEmpty()) {
            for (int i = 0; i < getFluidTanks().length; i++) {
                fluidLastTick.add(i, FluidStack.EMPTY);
            }
        }

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 57, 38));
            addSlot(new SlotItemHandler(iItemHandler, 1, 121, 17));
            addSlot(new SlotItemHandler(iItemHandler, 2, 121, 53));
            addSlot(new SlotItemHandler(iItemHandler, 3, 4, 4));
            addSlot(new SlotItemHandler(iItemHandler, 4, 4, 22));
            addSlot(new SlotItemHandler(iItemHandler, 5, 4, 40));
            addSlot(new SlotItemHandler(iItemHandler, 6, 4, 58));
        });

        addDataSlots(te.data);
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return te.tanks;
    }

    @Override
    public int getEnergyUsage() {
        return te.getEnergyUsageFinal();
    }

    @Override
    public int getMaxEnergy() {
        return te.getEnergyCapacity();
    }

    @Override
    public int getProgress() {
        return te.data.get(0);
    }

    @Override
    public int getProcessTime() {
        return te.data.get(1);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {//TODO
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex > 35) {
                if (!this.moveItemStackTo(itemstack1, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof UpgradeItem) {
                if (!this.moveItemStackTo(itemstack1, 39, 43, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof BucketItem) {
                if (((BucketItem) itemstack1.getItem()).getFluid().equals(Fluids.EMPTY)) {
                    if (!this.moveItemStackTo(itemstack1, 38, 39, true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(itemstack1, 37, 38, true)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.moveItemStackTo(itemstack1, 36, 17, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }
        return itemstack;
    }
}
