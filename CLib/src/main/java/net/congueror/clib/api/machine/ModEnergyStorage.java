package net.congueror.clib.api.machine;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class ModEnergyStorage extends EnergyStorage implements INBTSerializable<Tag> {

    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ModEnergyStorage(int capacity, int maxReceive, int MaxExtract) {
        super(capacity, maxReceive, 0);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    protected void onEnergyChanged() {

    }

    public void generateEnergy(long energy) {
        this.energy += energy;
        if(this.energy > capacity)
            this.energy = capacity;
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if(this.energy < 0)
        {
            this.energy = 0;
        }
    }

    public boolean isFullEnergy() {
        return getEnergyStored() >= getMaxEnergyStored();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("value", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof CompoundTag tag))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        setEnergy(tag.getInt("value"));
    }
}
