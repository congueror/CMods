package com.congueror.clib.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ModEnergyStorage(int capacity, int maxReceive, int MaxExtract) {
        super(capacity, maxReceive, 0);
    }

    public void setEnergy(int energy)
    {
        this.energy = energy;
        onEnergyChanged();
    }

    protected void onEnergyChanged() {

    }

    public void generateEnergy(long energy)
    {
        this.energy += energy;
        if(this.energy > capacity)
            this.energy = capacity;
        onEnergyChanged();
    }

    public void consumeEnergy(int energy)
    {
        this.energy -= energy;
        if(this.energy < 0)
        {
            this.energy = 0;
        }
    }

    public boolean isFullEnergy()
    {
        return getEnergyStored() >= getMaxEnergyStored();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("value", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        setEnergy(nbt.getInt("value"));
    }
}
