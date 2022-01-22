package net.congueror.clib.api.capability;

import net.minecraft.world.item.ItemStack;

public interface IEnergyItem {
    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of energy to be inserted.
     * @param simulate
     *            If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate);

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract
     *            Maximum amount of energy to be extracted.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    int extractEnergy(ItemStack stack, int maxExtract, boolean simulate);

    /**
     * Returns the amount of energy currently stored.
     */
    int getEnergyStored(ItemStack stack);

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    int getMaxEnergyStored(ItemStack stack);

    /**
     * Returns the maximum amount of energy that can be accepted per tick.
     */
    default int getMaxReceive(ItemStack stack) {
        return getMaxEnergyStored(stack) / 10;
    }

    /**
     * Returns the maximum amount of energy that can be extracted per tick.
     */
    default int getMaxExtract(ItemStack stack) {
        return getMaxEnergyStored(stack) / 10;
    }
}
