package net.congueror.cgalaxy.block.fuel_loader;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.clib.api.objects.machine_objects.fluid.AbstractFluidContainer;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class FuelLoaderContainer extends AbstractFluidContainer<FuelLoaderBlockEntity> {
    FuelLoaderBlockEntity te;

    public FuelLoaderContainer(int id, Player player, Inventory playerInventory, FuelLoaderBlockEntity tile, ContainerData dataIn) {
        super(CGContainerInit.FUEL_LOADER.get(), id, player, playerInventory, tile, dataIn);
        this.te = tile;
        if (fluidLastTick.isEmpty()) {
            for (int i = 0; i < getFluidTanks().length; i++) {
                fluidLastTick.add(i, FluidStack.EMPTY);
            }
        }

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 120, 17));
            addSlot(new SlotItemHandler(iItemHandler, 1, 120, 53));
            addSlot(new SlotItemHandler(iItemHandler, 2, 4, 4));
            addSlot(new SlotItemHandler(iItemHandler, 3, 4, 22));
            addSlot(new SlotItemHandler(iItemHandler, 4, 4, 40));
            addSlot(new SlotItemHandler(iItemHandler, 5, 4, 58));
        });

        addDataSlots(te.data);
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return te.tanks;
    }

    @Override
    public int getEnergyUsage() {
        return te.getEnergyUsage();
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
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            if (pIndex <= 5) {
                if (!this.moveItemStackTo(stack1, 6, 37 + 6, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stack1.getItem() instanceof BucketItem) {
                    if (!this.moveItemStackTo(stack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack1.getItem() instanceof UpgradeItem) {
                    if (!this.moveItemStackTo(stack1, 2, 6, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        return stack;
    }
}
