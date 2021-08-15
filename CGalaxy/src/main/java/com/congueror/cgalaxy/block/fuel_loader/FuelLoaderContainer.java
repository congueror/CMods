package com.congueror.cgalaxy.block.fuel_loader;

import com.congueror.cgalaxy.init.ContainerInit;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketUpdateFluidTanks;
import com.congueror.clib.blocks.container.AbstractFluidContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class FuelLoaderContainer extends AbstractFluidContainer<FuelLoaderTileEntity> {
    FuelLoaderTileEntity te;

    public FuelLoaderContainer(int id, PlayerInventory playerInventory, FuelLoaderTileEntity tile, IIntArray dataIn) {
        super(ContainerInit.FUEL_LOADER.get(), id, playerInventory, tile, dataIn);
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
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < getFluidTanks().length; i++) {
            FluidStack stack = getFluidTanks()[i].getFluid();
            FluidStack stack1 = this.fluidLastTick.get(i);
            if (stack != stack1) {
                boolean stackChanged = !stack1.isFluidStackIdentical(stack);
                FluidStack stack2 = stack.copy();
                this.fluidLastTick.set(i, stack2);
                if (stackChanged) {
                    for (IContainerListener icontainerlistener : this.listeners) {
                        if (icontainerlistener instanceof ServerPlayerEntity) {
                            Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) icontainerlistener),
                                    new PacketUpdateFluidTanks(windowId, stack2.getFluid().getRegistryName(), stack2.getAmount(), i));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateTanks(ResourceLocation rl, int amount, int tankId) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(rl);
        if (fluid != null) {
            te.tanks[tankId].setFluid(new FluidStack(fluid, amount));
        } else {
            te.tanks[tankId].setFluid(FluidStack.EMPTY);
        }
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