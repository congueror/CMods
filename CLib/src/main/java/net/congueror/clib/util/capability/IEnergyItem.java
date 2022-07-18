package net.congueror.clib.util.capability;

import net.minecraft.world.item.ItemStack;

public interface IEnergyItem {
    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    default int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        if (!canReceive(stack)) return 0;
        int energy = getEnergyStored(stack);
        int energyReceived = Math.min(getMaxEnergyStored(stack) - energy, Math.min(getMaxReceive(stack), maxReceive));
        if (!simulate) {
            energy += energyReceived;
            stack.getOrCreateTag().putInt("energy", energy);
        }
        return energyReceived;
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract Maximum amount of energy to be extracted.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    default int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        if (!canExtract(stack)) return 0;
        int energy = getEnergyStored(stack);
        int energyExtracted = Math.min(energy, Math.min(getMaxExtract(stack), maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            stack.getOrCreateTag().putInt("energy", energy);
        }
        return energyExtracted;
    }

    /**
     * Returns the amount of energy currently stored.
     */
    default int getEnergyStored(ItemStack stack) {
        return stack.getOrCreateTag().getInt("energy");
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    int getMaxEnergyStored(ItemStack stack);

    /**
     * Returns the maximum amount of energy that can be accepted per tick.
     */
    default int getMaxReceive(ItemStack stack) {
        return getMaxEnergyStored(stack);
    }

    /**
     * Returns the maximum amount of energy that can be extracted per tick.
     */
    default int getMaxExtract(ItemStack stack) {
        return getMaxEnergyStored(stack);
    }

    /**
     * Returns if this storage can have energy extracted. If this is false, then any calls to extractEnergy will return 0.
     */
    default boolean canExtract(ItemStack stack) {
        return true;
    }

    /**
     * Used to determine if this storage can receive energy. If this is false, then any calls to receiveEnergy will return 0.
     */
    default boolean canReceive(ItemStack stack) {
        return true;
    }
}
