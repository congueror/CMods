package net.congueror.clib.util.capability;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemEnergyWrapper implements ICapabilityProvider, IEnergyStorage {
    private final ItemStack stack;
    private final IEnergyItem energy;

    public ItemEnergyWrapper(ItemStack stack, IEnergyItem item) {
        this.stack = stack;
        this.energy = item;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return energy.receiveEnergy(stack, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energy.extractEnergy(stack, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return energy.getEnergyStored(stack);
    }

    @Override
    public int getMaxEnergyStored() {
        return energy.getMaxEnergyStored(stack);
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityEnergy.ENERGY.orEmpty(cap, LazyOptional.of(() -> this));
    }
}