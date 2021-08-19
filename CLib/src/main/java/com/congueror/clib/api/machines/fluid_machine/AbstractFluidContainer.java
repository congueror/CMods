package com.congueror.clib.api.machines.fluid_machine;

import com.congueror.clib.api.machines.AbstractInventoryContainer;
import com.congueror.clib.util.ModEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.*;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

public abstract class AbstractFluidContainer<T extends AbstractFluidTileEntity> extends AbstractInventoryContainer {
    T tile;
    IIntArray data;
    protected NonNullList<FluidStack> fluidLastTick = NonNullList.create();

    public AbstractFluidContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, T tile, IIntArray dataIn) {
        super(type, id, playerInventory);
        this.tile = tile;
        this.data = dataIn;

        trackPower();
        layoutPlayerInventorySlots(28, 84);
    }

    public abstract FluidTank[] getFluidTanks();

    public abstract int getEnergyUsage();

    public abstract int getMaxEnergy();

    public abstract int getProgress();

    public abstract int getProcessTime();

    public abstract void updateTanks(ResourceLocation rl, int amount, int tankId);

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
