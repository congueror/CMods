package net.congueror.clib.api.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemEnergyStorage implements IEnergyStorage {
    protected ItemStack stack;
    protected IEnergyItem energy;

    public ItemEnergyStorage(ItemStack stack, IEnergyItem energy) {
        this.stack = stack;
        this.energy = energy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return energy.receiveEnergy(stack, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energy.receiveEnergy(stack, maxExtract, simulate);
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
}
